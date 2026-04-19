package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartItemRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.CartRepository;
import com.example.ecommerce.repo.ProductRepository;
import com.example.ecommerce.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> product = productRepository.findById(request.getProductId());
        if (product.isEmpty()){
            System.out.println("Product not found");
            return false;
        }
        Product product1 = product.get();
        if (product1.getProductQuantity() < request.getQuantity()){
            System.out.println("Product quantity less than requested quantity");
            return false;
        }

        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()){
            return false;
        }

        User user1 = user.get();

        CartItem existingCart = cartRepository.findByUserAndProduct(user1, product1);
        if (existingCart != null){
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
            existingCart.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(existingCart.getQuantity())));
            cartRepository.save(existingCart);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(user1);
            cartItem.setProduct(product1);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(Long.valueOf(userId));

        if (product.isPresent() && user.isPresent()){
            cartRepository.deleteByUserAndProduct(user.get(), product.get());
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItemsForUser(String userId) {
         return userRepository.findById(Long.valueOf(userId))
                 .map(cartRepository::findByUser)
                 .orElseGet(List::of);
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartRepository::deleteByUser);

    }
}
