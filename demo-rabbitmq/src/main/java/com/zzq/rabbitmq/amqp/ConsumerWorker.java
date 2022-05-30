package com.zzq.rabbitmq.amqp;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import com.zzq.rabbitmq.config.AmqpConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConsumerWorker {

    private final AmqpTemplate amqpTemplate;

    /**
     * retry 3 times
     */
    @RabbitListener(queues = AmqpConfig.QUEUE_FIRST)
    public void receiveQ1(String data, Message message) {
        try {
            //business logic code here
            log.info(StrUtil.format("consume info  {}", data));
            //int c = 10 / 0;
        } catch (Exception e) {
            Integer retry = Convert.toInt(message.getMessageProperties().getHeader("retry"), 0);
            if (retry < 3) {
                log.info(StrUtil.format("consume info  {} ,retry times :", data, retry), e);
                amqpTemplate.convertAndSend(AmqpConfig.QUEUE_FIRST, data, msg -> {
                    msg.getMessageProperties().setHeader("retry", retry + 1);
                    return msg;
                });
            }
        }

    }


    /**
     * manual ack
     */
    @RabbitListener(queues = AmqpConfig.QUEUE_SECOND)
    public void receiveQ2(String data, Channel channel, Message message) throws IOException {
        try {
            //business logic code here
            log.warn(StrUtil.format("consume warning  {}", data));
            //int c = 10 / 0;

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.warn(StrUtil.format("consume warning retrying  {}", data));
            //basicNack(long deliveryTag, boolean multiple, boolean requeue)
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }

    }

    /**
     * delay message
     */
    @RabbitListener(queues = AmqpConfig.QUEUE_THIRD)
    public void receiveQ3(String data) {
        //business logic code here
        log.error(StrUtil.format("consume error  {}, current time {}", data, LocalDateTimeUtil.now()));
    }

}
