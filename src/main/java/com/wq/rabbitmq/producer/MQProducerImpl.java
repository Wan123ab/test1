package com.wq.rabbitmq.producer;

import com.wq.rabbitmq.model.Msg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(value = SpringJUnit4ClassRunner.class)
//下面多引入classpath:springmvc.xml会报错，原因未知
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MQProducerImpl implements MQProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String queueKey, Object object) {

        try {
            amqpTemplate.convertAndSend(object);
            System.out.println("=========发送消息成功！===========");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Test
    public void test() {
        Msg msg = new Msg("1", "测试消息", new Date());
        while (true) {
            amqpTemplate.convertAndSend(msg);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
