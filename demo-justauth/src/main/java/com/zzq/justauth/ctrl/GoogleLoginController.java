package com.zzq.justauth.ctrl;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/oauth/google")
public class GoogleLoginController {


    private final AuthRequest authGoogleRequest;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public void githubLogin(HttpServletResponse response) throws IOException {
        String authorize = authGoogleRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorize);
    }


    /**
     * {
     *     "code":2000,
     *     "data":{
     *         "avatar":"",
     *         "email":"",
     *         "gender":"",
     *         "location":"",
     *         "nickname":"",
     *         "rawUserInfo":{
     *             "sub":"",
     *             "email_verified":true,
     *             "name":"",
     *             "given_name":"",
     *             "locale":"",
     *             "picture":"",
     *             "email":""
     *         },
     *         "source":"GOOGLE",
     *         "token":{
     *             "accessToken":"",
     *             "expireIn":6346,
     *             "idToken":"",
     *             "scope":"",
     *             "tokenType":"Bearer"
     *         },
     *         "username":"",
     *         "uuid":""
     *     }
     * }
     *
     * @param callback
     * @return
     */
    @RequestMapping("/callback")
    public Object login(AuthCallback callback) throws JsonProcessingException {
        AuthResponse login = authGoogleRequest.login(callback);
        if (login.ok()) {
            Object data = login.getData();
            String json = objectMapper.writeValueAsString(data);
            AuthUser authUser = objectMapper.readValue(json, AuthUser.class);
            AuthToken token = authUser.getToken();
            String accessToken = token.getAccessToken();
            JSONObject rawUserInfo = authUser.getRawUserInfo();
            String locale = rawUserInfo.getObject("locale", String.class);

            log.info("accessToken :" + accessToken);
            log.info("locale :" + locale);
            log.info("userName :" + authUser.getUsername());
        }
        return login;
    }
}
