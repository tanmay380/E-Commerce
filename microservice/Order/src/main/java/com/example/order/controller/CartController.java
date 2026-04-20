package com.example.order.controller;


import com.example.order.dto.CartItemRequest;
import com.example.order.model.CartItem;
import com.example.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request
    ){
        if (!cartService.addToCart(userId, request))
            return ResponseEntity.badRequest().body("Product Out of Stock Ot User not found");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItemsForUser(
            @RequestHeader("X-User-ID") String userId){
        List<CartItem> items = cartService.getCartItemsForUser(userId);
        return items.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(items);

    }

}
