package com.parimal.ecom.service;

import com.parimal.ecom.dto.CartItemRequest;
import com.parimal.ecom.model.CartItem;
import com.parimal.ecom.model.Product;
import com.parimal.ecom.model.User;
import com.parimal.ecom.repository.CartItemRepository;
import com.parimal.ecom.repository.ProductRepository;
import com.parimal.ecom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(ProductRepository productRepository, CartItemRepository cartItemRepository,
                           UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addToCart(Long userId, CartItemRequest request) {

         // Product Validation
         Optional<Product> productsOpt =  productRepository.findById(request.getProductId());
         if (productsOpt.isEmpty()){
             return false;
         }
         Product product = productsOpt.get();

         // Stock Validation
         if (product.getStockQuantity() < request.getQuantity()){
            return false;
         }

         //User Validation
         Optional<User> userOpt = userRepository.findById(userId);
         if (userOpt.isEmpty()){
                return false;
         }
         User user = userOpt.get();

         // Check if CartItem already exists for this user and product
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            // Update quantity and price
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;

    }
}
