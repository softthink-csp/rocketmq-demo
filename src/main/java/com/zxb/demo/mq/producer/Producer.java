package com.zxb.demo.mq.producer;

import com.zxb.demo.mq.config.RocketMQConfigure;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Producer {

    @Autowired
    private RocketMQConfigure configure;

    private DefaultMQProducer producer;

    public Producer() {
    }

    @PostConstruct
    public void init() {
        producer = new DefaultMQProducer(configure.getProducerGroup());
        producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr(configure.getNameserver());
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }


}
