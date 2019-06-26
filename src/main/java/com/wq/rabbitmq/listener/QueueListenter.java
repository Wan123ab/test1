package com.wq.rabbitmq.listener;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.wq.rabbitmq.converter.Gson2JsonMessageConverter;
import com.wq.rabbitmq.model.Msg;
import com.wq.rabbitmq.websocket.MyWebSocketHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 消费者消息监听器
 */
public class QueueListenter implements ChannelAwareMessageListener {

    @Autowired
    private Gson2JsonMessageConverter converter;

    @Override
    public void onMessage(Message message, Channel channel) {

        try {
            //System.out.println("=============成功接收到消息"+new String(message.getBody(),"UTF-8")+"=============");

            Msg msg = converter.fromMessage(message, Msg.class);
            String json = new Gson().toJson(msg);
            System.out.println("=============成功接收到消息===========" + json);

            //通过websocket推送消息给所有在线用户
            for (WebSocketSession webSocketSession : MyWebSocketHandler.userSocketSessionMap.values()) {
                webSocketSession.sendMessage(new TextMessage(json));
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
