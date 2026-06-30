package com.isaachome.orderservice.publish;

import com.isaachome.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    @Value("${rabbitmq.exchange.order.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key.order.name}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;


    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEvent(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event sent to RabbitMQ => %s", orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
    }
}
