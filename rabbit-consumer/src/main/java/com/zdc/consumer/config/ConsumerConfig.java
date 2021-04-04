package com.zdc.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rabbit.properties")
public class ConsumerConfig {

    @Value("${rabbit.url}")
    private String url;

    @Bean("cachingConnectionFactory")
    public CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUri(url);
        return cachingConnectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(@Qualifier("cachingConnectionFactory") ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return  rabbitTemplate;

    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("cachingConnectionFactory") ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        rabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认ACK
        rabbitListenerContainerFactory.setAutoStartup(true);
        return rabbitListenerContainerFactory;
    }

    //创建队列
    @Bean("firstQueue")
    public Queue firstQueue(){
        return new Queue("ZDC_FIRST_QUEUE");
    }

    @Bean("secondQueue")
    public Queue secondQueue(){
        return new Queue("ZDC_SECOND_QUEUE");
    }

    @Bean("thirdQueue")
    public Queue thirdQueue(){
        return new Queue("ZDC_THIRD_QUEUE");
    }
/**
 * 创建交换机
 */
//直连
    @Bean("directExchange")
    public DirectExchange getDirectExchange(){
        return new DirectExchange("DIRECT_BASIC_EXCHANGE");
    }

    //匹配
    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange("TOPIC_BASIC_EXCHANGE");
    }

    //广播
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("FANOUT_BASIC_EXCHANGE");
    }

    /**
     * 交换机和队列进行绑定
     */
    @Bean
    public Binding bindFirst(@Qualifier("firstQueue")Queue queue,@Qualifier("directExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("zdc");
    }

    @Bean
    public Binding bindSecond(@Qualifier("secondQueue")Queue queue,@Qualifier("topicExchange")TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("#.zdc.#");
    }


    @Bean
    public Binding bindThird(@Qualifier("thirdQueue")Queue queue,@Qualifier("fanoutExchange")FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
