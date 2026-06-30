package com.isaachome.orderservice.controller;

import com.isaachome.orderservice.dto.OrderDto;
import com.isaachome.orderservice.dto.OrderEvent;
import com.isaachome.orderservice.publish.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping()
    public String placeOrder(@RequestBody OrderDto orderDto) {
        String orderId = UUID.randomUUID().toString();
        OrderDto orderDtoWithId = new OrderDto(
                orderId,
                orderDto.orderName(),
                orderDto.quantity(),
                orderDto.price()
        );
        var orderEvent = new OrderEvent("pending", "New order placed", orderDtoWithId);
        orderProducer.publishEvent(orderEvent);
        return "Order placed successfully with ID: " + orderId;
    }
}
