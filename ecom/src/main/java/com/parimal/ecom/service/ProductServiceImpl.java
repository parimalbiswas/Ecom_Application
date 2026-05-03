package com.parimal.ecom.service;

import com.parimal.ecom.dto.ProductRequest;
import com.parimal.ecom.dto.ProductResponse;
import com.parimal.ecom.model.Product;
import com.parimal.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
            this.productRepository = productRepository;
    }

    

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = convertToEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertToResponse).orElse(null);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = convertToEntity(productRequest);
            product.setId(id);
            Product updatedProduct = productRepository.save(product);
            return convertToResponse(updatedProduct);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    
    // Helper method to convert Product entity to ProductResponse DTO
    private ProductResponse convertToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().toString(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl()
        );
    }
    
    //ProductRequest to Product
    private Product convertToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(new BigDecimal(productRequest.getPrice()));
        product.setStockQuantity(productRequest.getStockQuantity());

        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setActive(productRequest.getActive() != null ? productRequest.getActive() : true);
        return product;
    }
}
