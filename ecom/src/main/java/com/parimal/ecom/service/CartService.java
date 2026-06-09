package com.parimal.ecom.service;

import com.parimal.ecom.dto.CartItemRequest;

public interface CartService {

    void addToCart(Long userId, CartItemRequest request);

}
