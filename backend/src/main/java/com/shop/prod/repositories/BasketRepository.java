package com.shop.prod.repositories;

import com.shop.prod.models.Basket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Basket b WHERE b.expirationTime < CURRENT_TIMESTAMP")
//    void deleteExpiredBaskets();
}
