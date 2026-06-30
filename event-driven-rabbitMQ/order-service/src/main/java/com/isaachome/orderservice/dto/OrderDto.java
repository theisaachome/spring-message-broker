package com.isaachome.orderservice.dto;

public record OrderDto(
        String orderId,
        String orderName,
        Integer quantity,
        Double price
) {
}
