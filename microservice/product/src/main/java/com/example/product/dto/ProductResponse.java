package com.example.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String imageUrl;
    private String brand;
    private BigDecimal price;
    private boolean active;
    private Integer productQuantity;
}
