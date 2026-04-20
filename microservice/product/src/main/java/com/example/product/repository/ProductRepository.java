package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("Select p from Product p where p.active = true AND p.productQuantity > 0 AND lower(p.title) like lower(Concat('%',:keyword,'%'))")
    List<Product> seachProducts(@Param("keyword") String keyword);
}
