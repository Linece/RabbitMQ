package com.zdc.consumer.server;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"ZDC_THIRD_QUEUE"})
public class ThirdConsumer {

    @RabbitHandler
    public void process(String msg){
        System.out.println("######################################################################################this is third msg "+msg);
    }
}
