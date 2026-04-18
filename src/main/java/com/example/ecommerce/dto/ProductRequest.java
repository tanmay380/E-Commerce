package com.example.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private String category;
    private String imageUrl;
    private String brand;
    private BigDecimal price;
    private Integer productQuantity;
}
