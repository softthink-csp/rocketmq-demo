package com.zxb.demo.controller;

import com.zxb.demo.mq.config.RocketMQConfigure;
import com.zxb.demo.mq.producer.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private Producer producer;
    @Autowired
    private RocketMQConfigure configure;

    @RequestMapping("/mq/test")
    public String hello() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<String> msgs = Arrays.asList(
                "message1", "message2", "message3"
        );
        for (String msg : msgs) {
            Message message = new Message(configure.getTopic(), "test_tag", msg.getBytes());
            SendResult sendResult = producer.getProducer().send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    return list.get(0);
                }
            }, 0);
        }
        return "消费完成";
    }
}
