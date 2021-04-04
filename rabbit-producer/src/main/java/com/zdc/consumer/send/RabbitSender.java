package com.zdc.consumer.send;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdc.consumer.entity.Merchant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    private final static String directExchange = "DIRECT_BASIC_EXCHANGE";

    private final static String topicExchange = "TOPIC_BASIC_EXCHANGE";

    private final static String fanoutExchange = "FANOUT_BASIC_EXCHANGE";

    private final static String directRoutingKey = "zdc";

    private final static String topicRoutingKey = "asd.zdc.qqq";

    private final static String fanoutRoutingKey = "";

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send()throws Exception{
        Merchant merchant = new Merchant(1001,"this is a direct msg :你好！","沧海桑田");
        //直连方式
        amqpTemplate.convertAndSend(directExchange,directRoutingKey,merchant);

        amqpTemplate.convertAndSend(topicExchange,topicRoutingKey,"this is top msg :谁等.zdc.是否");

        //发送json字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(merchant);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@:"+json);
        amqpTemplate.convertAndSend(fanoutExchange,"",json);
    }
}
