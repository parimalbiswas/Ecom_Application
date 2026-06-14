package com.parimal.ecom.repository;

import com.parimal.ecom.model.CartItem;
import com.parimal.ecom.model.Product;
import com.parimal.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(User user, Product product);

}
