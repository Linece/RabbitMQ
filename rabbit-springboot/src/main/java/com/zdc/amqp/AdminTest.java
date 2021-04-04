package com.zdc.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.zdc.amqp")
public class AdminTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdminTest.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
//        //申明一个交换机
//        rabbitAdmin.declareExchange(new DirectExchange("ZDC_ADMIN_EXCHANGE",false,false));
//
//        //声明一个队列
//        rabbitAdmin.declareQueue(new Queue("ZDC_ADMIN_QUEUE",false,false,false));
//
//        //声明一个绑定
//        rabbitAdmin.declareBinding(new Binding("ZDC_ADMIN_QUEUE",Binding.DestinationType.QUEUE,"ZDC_ADMIN_EXCHANGE","admin",null));

        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("","ZDC_BASIC_FIRST_QUEUE","is a direct msg");

        rabbitTemplate.convertAndSend("TOPIC_BASIC_EXCHANGE","asd.zdc.asd","the top msg asd");

        rabbitTemplate.convertAndSend("FANOUT_BASIC_EXCHANGE","","this is a fanout msg ............");

        for(;;){

            Object obj = rabbitTemplate.receiveAndConvert("ZDC_BASIC_SECOND_QUEUE");
            if (obj != null){
                System.out.println("@@@@@@@@@@@@@@@@@@@@:"+obj);
            }else {
                break;
            }
        }

    }
}

