package com.zzq.websocket.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@ServerEndpoint("/api/pushMessage/{userId}")
public class WebSocketServer {

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String userId = "";

    /**
     * The method called when the connection is established successfully
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);

            webSocketMap.put(userId, this);
        } else {

            webSocketMap.put(userId, this);

            addOnlineCount();
        }
        log.info("user connected: " + userId + ", current online count:" + getOnlineCount());
        sendMessage("connection successful");
    }

    /**
     * connection closed method to call
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("user  leaving: Id" + userId + " ,current online count:" + getOnlineCount());
    }

    /**
     * Method to be called when a client message is received
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("current user :" + userId + ", message:" + message);

        if (StringUtils.isNotBlank(message)) {
            try {
                JSONObject jsonObject = JSON.parseObject(message);
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");

                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(message);
                } else {
                    log.error("toUserId: " + toUserId + " is not online");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error userId: " + this.userId + ", reason: " + error.getMessage());
        error.printStackTrace();
    }


    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}

