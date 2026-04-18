package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedPro = productRepository.save(product);
        return mapToProductResponse(savedPro);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(ep -> {
                    updateProductFromRequest(ep, productRequest);
                    Product product = productRepository.save(ep);
                    return mapToProductResponse(ep);
                });
    }

    private ProductResponse mapToProductResponse(Product savedPro) {
        return ProductResponse.builder()
                .id(savedPro.getId())
                .title(savedPro.getTitle())
                .description(savedPro.getDescription())
                .category(savedPro.getCategory())
                .imageUrl(savedPro.getImageUrl())
                .price(savedPro.getPrice())
                .active(savedPro.isActive())
                .productQuantity(savedPro.getProductQuantity())
                .build();
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());
        product.setProductQuantity(productRequest.getProductQuantity());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(p -> {
                    p.setActive(false);
                    productRepository.save(p);
                    return true;
                })
                .orElse(false);

    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.seachProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}
