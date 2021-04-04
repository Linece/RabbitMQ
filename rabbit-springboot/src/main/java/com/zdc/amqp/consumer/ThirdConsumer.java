package com.zdc.amqp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"ZDC_BASIC_THIRD_QUEUE"})
public class ThirdConsumer {

    public void process(String msg){
        System.out.println("######################################################################################this is third msg "+msg);
    }
}
