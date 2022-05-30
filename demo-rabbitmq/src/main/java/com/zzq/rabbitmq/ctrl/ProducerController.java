package com.zzq.rabbitmq.ctrl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.zzq.rabbitmq.config.AmqpConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final AmqpTemplate amqpTemplate;

    /**
     *  retry 3 times
     */
    @GetMapping("/sendToInfo/{data}")
    public void sendToInfo(@PathVariable String data) {

        amqpTemplate.convertAndSend(AmqpConfig.EXCHANGE_FIRST, AmqpConfig.ROUTING_KEY_INFO, data);

    }

    /**
     * manual ack
     */

    @GetMapping("/sendToWarning/{data}")
    public void sendToWarning(@PathVariable String data) {

        amqpTemplate.convertAndSend(AmqpConfig.EXCHANGE_FIRST, AmqpConfig.ROUTING_KEY_WARNING, data);

    }

    /**
     * Delay Message
     * delay 5 seconds
     */
    @GetMapping("/sendToError/{data}")
    public void sendToError(@PathVariable String data) {

        log.error("sendToError: {} , current time {}", data, LocalDateTimeUtil.now());
        amqpTemplate.convertAndSend(AmqpConfig.EXCHANGE_DELAY, AmqpConfig.ROUTING_DELAY, data, msg -> {
                    msg.getMessageProperties().setExpiration("5000");
                    return msg;
                }
        );

    }
}
