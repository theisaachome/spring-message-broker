package com.isaachome.orderservice.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.order.name}")
    private String orderQueueName;
    @Value("${rabbitmq.exchange.order.name}")
    private String orderExchangeName;
    @Value("${rabbitmq.routing.key.order.name}")
    private String routingKey;
    // define spring-beans for Queues:
    // order queue
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueueName);
    }
    // order exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchangeName);
    }
    // order binding for binding queue with exchange
    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(routingKey);
    }
    // message converter
    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
    // configure rabbit template
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    // configure rabbit listener container factory


}
