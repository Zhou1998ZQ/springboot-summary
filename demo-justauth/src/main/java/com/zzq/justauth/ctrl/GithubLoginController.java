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
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/github")
public class GithubLoginController {

    private final AuthRequest authGithubRequest;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public void githubLogin(HttpServletResponse response) throws IOException {
        String authorize = authGithubRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorize);
    }


    /**
     * {
     * "code":2000,
     * "msg":null,
     * "data":{
     * "uuid":"",
     * "username":"Zhou1998ZQ",
     * "nickname":null,
     * "avatar":"https://avatars.githubusercontent.com/u/103564579?v=4",
     * "blog":"",
     * "company":null,
     * "location":null,
     * "email":null,
     * "remark":null,
     * "gender":"UNKNOWN",
     * "source":"GITHUB",
     * "token":{
     * "accessToken":"",
     * "expireIn":0,
     * "refreshToken":null,
     * "refreshTokenExpireIn":0,
     * "uid":null,
     * "openId":null,
     * "accessCode":null,
     * "unionId":null,
     * "scope":null,
     * "tokenType":"bearer",
     * "idToken":null,
     * "macAlgorithm":null,
     * "macKey":null,
     * "code":null,
     * "oauthToken":null,
     * "oauthTokenSecret":null,
     * "userId":null,
     * "screenName":null,
     * "oauthCallbackConfirmed":null
     * },
     * "rawUserInfo":{
     * "gists_url":"",
     * "repos_url":"",
     * "following_url":"",
     * "twitter_username":null,
     * "bio":null,
     * "created_at":"",
     * "login":"",
     * "type":"",
     * "blog":"",
     * "subscriptions_url":"",
     * "updated_at":"",
     * "site_admin":false,
     * "company":null,
     * "id":103564579,
     * "public_repos":1,
     * "gravatar_id":"",
     * "email":null,
     * "organizations_url":"",
     * "hireable":null,
     * "starred_url":"",
     * "followers_url":"",
     * "public_gists":0,
     * "url":"",
     * "received_events_url":"",
     * "followers":1,
     * "avatar_url":"",
     * "events_url":"",
     * "html_url":"",
     * "following":1,
     * "name":null,
     * "location":null,
     * "node_id":""
     * }
     * }
     * }
     *
     * @param callback
     * @return
     */
    @RequestMapping("/callback")
    public Object login(AuthCallback callback) throws JsonProcessingException {
        AuthResponse login = authGithubRequest.login(callback);
        if (login.ok()) {
            Object data = login.getData();
            String json = objectMapper.writeValueAsString(data);
            AuthUser authUser = objectMapper.readValue(json, AuthUser.class);
            AuthToken token = authUser.getToken();
            String accessToken = token.getAccessToken();
            JSONObject rawUserInfo = authUser.getRawUserInfo();
            LocalDateTime updatedAt = rawUserInfo.getObject("updated_at", LocalDateTime.class);

            log.info("accessToken :" + accessToken);
            log.info("updatedAt :" + updatedAt);
            log.info("userName :" + authUser.getUsername());
        }
        return login;
    }
}
