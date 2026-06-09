package com.parimal.ecom.controller;

import com.parimal.ecom.dto.CartItemRequest;
import com.parimal.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    public ResponseEntity<Void> addToCart(@RequestHeader("X-User-Id") Long userId,
                                      @RequestBody CartItemRequest request) {

        cartService.addToCart(userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
