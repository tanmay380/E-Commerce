package com.example.ecommerce.dto;

import com.example.ecommerce.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal amount;
    private OrderStatus status;
    private List<OrderDTO> items;
    private LocalDateTime createdAt;

}
