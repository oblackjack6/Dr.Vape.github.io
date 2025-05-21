package com.shop.prod.repositories;

import com.shop.prod.models.BasketItem;
import com.shop.prod.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Integer> {
    List<BasketItem> findByProduct(Product product);
}
