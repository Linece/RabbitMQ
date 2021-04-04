package com.zdc.consumer.server;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = {"ZDC_SECOND_QUEUE"})
public class SecondConsumer {

    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {
        System.out.println("##############################################################################this is second msg "+msg);
        //消费者消息确认，
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
