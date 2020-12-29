package com.zxb.demo.mq.consumer;

import com.zxb.demo.mq.config.RocketMQConfigure;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class Consumer {

    @Autowired
    private RocketMQConfigure configure;

    public Consumer(){
    }

    @PostConstruct
    public void init()  throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(configure.getConsumer());
        consumer.setNamesrvAddr(configure.getNameserver());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe(configure.getTopic(), "*");
        consumer.registerMessageListener((MessageListenerConcurrently)(msgs, context) -> {
            try {
                for (Message msg : msgs) {
                    String body = new String(msg.getBody(), "utf-8");
                    System.out.println(String.format("获取一条消息，主题为：%s, 内容为：%s", msg.getTopic(), body));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("消费者启动成功");
    }
}
