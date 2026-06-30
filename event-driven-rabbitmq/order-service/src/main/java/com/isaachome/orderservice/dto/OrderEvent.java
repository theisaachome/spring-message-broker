package com.isaachome.orderservice.dto;

public record OrderEvent(
        String orderStatus, // pending, completed, failed, in-progress
        String message,
        OrderDto orderDto
) {
}
