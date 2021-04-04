package com.zdc.consumer.server;

import com.rabbitmq.client.Channel;
import com.zdc.consumer.entity.Merchant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues={"ZDC_FIRST_QUEUE"})
public class FirstConsumer {

    @RabbitHandler
    public void process(Merchant merchant, Channel channel, Message message) throws Exception{
        //channel.basicAck(deliveryTag,true);
        System.out.println("#################################################################first queue get msg "+merchant.toString());
        //消费者--》消息确认！
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
