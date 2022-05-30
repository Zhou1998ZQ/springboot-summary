package com.zzq.email.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zzq.email.config.MailAccountProperties;
import com.zzq.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
@ConditionalOnBean(MailAccountProperties.class)
public class EmailServiceImpl implements EmailService {

    private final MailAccount mailAccount;

    @Override
    public void sendEmail(String emailAddress) {

        MailUtil.send(mailAccount, CollUtil.newArrayList(emailAddress), "Zhou-Test", scopeTemplate(), true);
    }

    @SuppressWarnings("all")
    public String scopeTemplate() {
        String filename = "static\\tamplate.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long round = Math.round((Math.random() + 1) * 1000);
        String contentText = round + "";


        String inscription = "Test-Zhou";
        return MessageFormat.format(buffer.toString(), contentText, inscription);
    }
}
