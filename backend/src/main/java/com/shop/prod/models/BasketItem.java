package com.shop.prod.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @ManyToOne
    @JsonBackReference(value = "basket-items")
    private Basket basket;
}
