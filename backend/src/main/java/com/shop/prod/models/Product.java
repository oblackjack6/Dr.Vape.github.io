package com.shop.prod.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Optional;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imgUrl;

    @Column(name = "title", unique = true)
    private String title;

    @Column(nullable = false)
    @Convert(converter = ProductCategoryConverter.class)
    private ProductCategories category;

    private Double price;

    private Integer quantity;

    public Product update(Product newProductData) {
        Optional.ofNullable(newProductData.getImgUrl()).ifPresent(this::setImgUrl);
        Optional.ofNullable(newProductData.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(newProductData.getCategory()).ifPresent(this::setCategory);
        Optional.ofNullable(newProductData.getPrice()).ifPresent(this::setPrice);
        Optional.ofNullable(newProductData.getQuantity()).ifPresent(this::setQuantity);

        return this;
    }
}
