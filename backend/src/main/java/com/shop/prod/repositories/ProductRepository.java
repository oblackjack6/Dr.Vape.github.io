package com.shop.prod.repositories;

import com.shop.prod.models.Product;
import com.shop.prod.models.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByTitle(String title);

    List<Product> findByCategory(ProductCategories category);
}
