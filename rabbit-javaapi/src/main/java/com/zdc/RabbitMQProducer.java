package com.zdc;

import com.MyUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMQProducer {

    private static String QUEUE_NAME = "TASK_ACK_QUUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(MyUtil.getVal("rabbitmq.url"));

        //建立连接
        Connection connection = factory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        String msg = "this is my rabbit";

        //申明队列（默认交换机AMQP default,Direct）
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息
        for (int i=0;i<5;i++){
            channel.basicPublish("",QUEUE_NAME,null,(msg+i).getBytes());
        }
        channel.close();
        connection.close();
    }

}
