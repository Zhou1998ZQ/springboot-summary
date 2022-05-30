package com.zzq.justauth.ctrl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/wechat")
public class WechatLoginController {

    private final AuthRequest authWechatRequest;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public void githubLogin(HttpServletResponse response) throws IOException {
        String authorize = authWechatRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorize);
    }


    /**
     * {
     * 	"code": 2000,
     * 	"msg": null,
     * 	"data": {
     * 		"uuid": "",
     * 		"username": "",
     * 		"nickname": "",
     * 		"avatar": "",
     * 		"blog": null,
     * 		"company": null,
     * 		"location": "",
     * 		"email": null,
     * 		"remark": null,
     * 		"gender": "",
     * 		"source": "",
     * 		"token": {
     * 			"accessToken": "",
     * 			"expireIn": 4251,
     * 			"refreshToken": "",
     * 			"uid": null,
     * 			"openId": "",
     * 			"accessCode": null,
     * 			"unionId": "",
     * 			"scope": null,
     * 			"tokenType": null,
     * 			"idToken": null,
     * 			"macAlgorithm": null,
     * 			"macKey": null,
     * 			"code": null,
     * 			"oauthToken": null,
     * 			"oauthTokenSecret": null,
     * 			"userId": null,
     * 			"screenName": null,
     * 			"oauthCallbackConfirmed": null
     *                }* 	}
     * }
     *
     * @param callback
     * @return
     */
    @RequestMapping("/callback")
    public Object login(AuthCallback callback) throws JsonProcessingException {
        AuthResponse login = authWechatRequest.login(callback);
        if (login.ok()) {
            Object data = login.getData();
            String json = objectMapper.writeValueAsString(data);
            AuthUser authUser = objectMapper.readValue(json, AuthUser.class);
            AuthToken token = authUser.getToken();
            String accessToken = token.getAccessToken();

            log.info("accessToken :" + accessToken);
            log.info("location :" + authUser.getLocation());
            log.info("userName :" + authUser.getUsername());
        }
        return login;
    }
}
