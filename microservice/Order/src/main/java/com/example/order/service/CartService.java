package com.example.order.service;


import com.example.order.dto.CartItemRequest;
import com.example.order.model.CartItem;
import com.example.order.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
//        Optional<Product> product = productRepository.findById(request.getProductId());
//        if (product.isEmpty()){
//            System.out.println("Product not found");
//            return false;
//        }
//        Product product1 = product.get();
//        if (product1.getProductQuantity() < request.getQuantity()){
//            System.out.println("Product quantity less than requested quantity");
//            return false;
//        }
//
//        Optional<User> user = userRepository.findById(Long.valueOf(userId));
//        if (user.isEmpty()){
//            return false;
//        }
//
//        User user1 = user.get();

        CartItem existingCart = cartRepository.findByUserIdAndProductId(userId, String.valueOf(request.getProductId()));
        if (existingCart != null){
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
//            existingCart.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(existingCart.getQuantity())));
            existingCart.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(existingCart);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(String.valueOf(request.getProductId()));
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
//        Optional<Product> product = productRepository.findById(productId);
//        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        CartItem item = cartRepository.findByUserIdAndProductId(userId, String.valueOf(productId));

        if (item!=null){
            cartRepository.delete(item);
            return true;
        }

//        if (product.isPresent() && user.isPresent()){
//            cartRepository.deleteByUserAndProduct(user.get(), product.get());
//            return true;
//        }
        return false;
    }

    public List<CartItem> getCartItemsForUser(String userId) {
         return cartRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
//        userRepository.findById(Long.valueOf(userId)).ifPresent(cartRepository::deleteByUser);
            cartRepository.deleteByUserId(userId);
    }
}
