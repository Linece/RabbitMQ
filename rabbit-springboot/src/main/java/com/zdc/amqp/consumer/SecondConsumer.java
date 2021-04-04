package com.zdc.amqp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"ZDC_BASIC_SECOND_QUEUE"})
public class SecondConsumer {

    @RabbitHandler
    public void process(String msg){
        System.out.println("##############################################################################this is second msg "+msg);
    }
}
