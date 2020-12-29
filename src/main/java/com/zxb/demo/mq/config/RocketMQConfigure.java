package com.zxb.demo.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rocket")
@Data
public class RocketMQConfigure {

    public RocketMQConfigure() {
    }
    private String nameserver;
    private String topic;
    private String producerGroup;
    private String consumer;

}
