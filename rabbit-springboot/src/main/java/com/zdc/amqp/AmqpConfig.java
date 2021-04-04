package com.zdc.amqp;

import com.zdc.util.MyUtil;
import org.apache.logging.log4j.message.SimpleMessageFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AmqpConfig {

    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(MyUtil.getVal("rabbitmq.url"));
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);//注册一个回调函数
        return admin;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return  rabbitTemplate;

    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return null;
            }
        });
        return container;
    }

    //注册交换机
    @Bean("topicExchange")
    public TopicExchange getTopicExchange(){
        return new TopicExchange("TOPIC_BASIC_EXCHANGE");
    }

    @Bean("fanoutExchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("FANOUT_BASIC_EXCHANGE");
    }

    @Bean("directExchange")
    public DirectExchange getDirectExchange(){
        return new DirectExchange("DIRECT_BASIC_CHANGE");
    }
    //注册队列
    @Bean("firstQueue")
    public Queue getFirstQueue(){
        Map<String,Object> args = new HashMap<String,Object>();
        args.put("x-message-ttl",6000);
        Queue queue = new Queue("ZDC_BASIC_FIRST_QUEUE",false,false,true,args);
        return queue;
    }

    @Bean("secondQueue")
    public  Queue getSecondQueue(){
        return new Queue("ZDC_BASIC_SECOND_QUEUE",false,false,true,null);
    }

    @Bean("thirdQueue")
    public Queue getThirdQueue(){
        return new Queue("ZDC_BASIC_THIRD_QUEUE");
    }

    //交换机于队列进行绑定
    @Bean
    public Binding bindSecond(@Qualifier("secondQueue")Queue queue,@Qualifier("topicExchange") TopicExchange topicExchange){

        return BindingBuilder.bind(queue).to(topicExchange).with("#.zdc.#");
    }

    @Bean
    public Binding bindThird(@Qualifier("thirdQueue")Queue queue,@Qualifier("fanoutExchange")FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }


}
