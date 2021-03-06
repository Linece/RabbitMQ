package com.zdc.amqp.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues={"ZDC_BASIC_FIRST_QUEUE"})
public class FirstConsumer {

    @RabbitHandler
    public void process(String msg, Channel channel,long deliveryTag) throws Exception{
        channel.basicAck(deliveryTag,true);
        System.out.println("#################################################################first queue get msg "+msg);
    }
}
