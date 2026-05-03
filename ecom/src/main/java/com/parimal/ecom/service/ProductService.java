package com.parimal.ecom.service;

import com.parimal.ecom.dto.ProductRequest;
import com.parimal.ecom.dto.ProductResponse;

import java.util.List;


public interface ProductService {

    ProductResponse addProduct(ProductRequest product);

     List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest product);

     boolean deleteProduct(Long id);



}
