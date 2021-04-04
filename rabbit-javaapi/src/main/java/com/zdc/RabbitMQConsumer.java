package com.zdc;

import com.MyUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMQConsumer {

    private final static String QUEUE_NAME = "TASK_ACK_QUUE";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(MyUtil.getVal("rabbitmq.url"));

        //建立连接
        Connection connection = factory.newConnection();
        //创建消息通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        System.out.println("wait msg..........................");

        //创建消费者，接收消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("this is the msg "+msg);
                if (msg.contains("拒收")){
                    channel.basicReject(envelope.getDeliveryTag(),false);
                }else if (msg.contains("异常")){
                    channel.basicNack(envelope.getDeliveryTag(),true,false);
                }else {
                    channel.basicAck(envelope.getDeliveryTag(),true);
                }


            }
        };
        //开始获取消息，这里开启了手工应答
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);

    }
}
