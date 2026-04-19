package com.example.ecommerce.repo;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user1, Product product1);

    void deleteByUserAndProduct(User u, Product p);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
