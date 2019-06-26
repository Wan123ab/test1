package com.wq.rabbitmq.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
public class WebSocketService {
    private Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @Autowired
    private MyWebSocketHandler handler;

    /**
     * @param @param  userId 用户id
     * @param @param  message 消息
     * @param @return 发送成功返回true，否则返回false
     * @Title: sendToAllTerminal
     * @Description: 调用websocket类给用户下的所有终端发送消息
     */
    public void sendToAllTerminal(int userId, String message) {
        logger.debug("向用户{}的消息：{}", userId, message);

        try {
            handler.sendMessageToUser(userId, new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
