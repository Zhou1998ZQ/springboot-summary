package com.zzq.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AmqpConfig {

    public static final String QUEUE_FIRST = "queue.first";
    public static final String QUEUE_SECOND = "queue.second";
    public static final String QUEUE_THIRD = "queue.third";
    public static final String EXCHANGE_FIRST = "exchange.first";

    public static final String QUEUE_DELAY = "queue.delay";
    public static final String EXCHANGE_DELAY = "exchange.delay";
    public static final String ROUTING_DELAY = "routing.delay";

    public final static String ROUTING_KEY_WARNING = "warning";
    public final static String ROUTING_KEY_INFO = "info";
    public final static String ROUTING_KEY_ERROR = "error";


    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    @Bean
    public Queue queueFirst() {
        return QueueBuilder
                .durable(QUEUE_FIRST)
                .build();
    }

    @Bean
    public Queue queueSecond() {
        return QueueBuilder
                .durable(QUEUE_SECOND)
                .build();
    }

    @Bean
    public Queue queueThird() {
        return QueueBuilder
                .durable(QUEUE_THIRD)
                .build();
    }

    @Bean
    public Queue queueDelay() {
        return QueueBuilder
                .durable(QUEUE_DELAY)
                .withArguments(Map.of("x-dead-letter-exchange", EXCHANGE_FIRST, "x-dead-letter-routing-key", ROUTING_KEY_ERROR))
                .build();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_FIRST).build();
    }


    @Bean
    public Exchange delayExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DELAY).build();
    }


    @Bean
    public Binding bindingDelay() {
        return BindingBuilder.bind(queueDelay()).to(delayExchange()).with(ROUTING_DELAY).noargs();
    }

    @Bean
    public Binding bindingWithInfo() {
        return BindingBuilder.bind(queueFirst()).to(exchange()).with(ROUTING_KEY_INFO).noargs();
    }

    @Bean
    public Binding bindingWithWarning() {
        return BindingBuilder.bind(queueSecond()).to(exchange()).with(ROUTING_KEY_WARNING).noargs();
    }


    @Bean
    public Binding bindingWithError() {
        return BindingBuilder.bind(queueThird()).to(exchange()).with(ROUTING_KEY_ERROR).noargs();
    }
}
