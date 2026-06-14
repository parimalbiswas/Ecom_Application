package com.parimal.ecom.controller;

import com.parimal.ecom.dto.CartItemRequest;
import com.parimal.ecom.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-Id") Long userId,
                                      @RequestBody CartItemRequest request) {

        if(cartService.addToCart(userId, request)){;
            return new ResponseEntity<>("Item added to cart successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to add item to cart. Please check product availability and user validity.", HttpStatus.BAD_REQUEST);
    }
}
