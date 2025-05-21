package com.shop.prod.controllers;

import com.shop.prod.dtos.BasketDto;
import com.shop.prod.models.Basket;
import com.shop.prod.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("basket")
public class BasketController {
    @Autowired
    BasketService basketService;

    @GetMapping("/{id}")
    public ResponseEntity<BasketDto> getBasket(@PathVariable Integer id){
        return basketService.getBasket(id);
    }

    @GetMapping()
    public ResponseEntity<List<BasketDto>> getAllBaskets(){
        return basketService.getAllBaskets();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<String> rejectBasket(@PathVariable Integer id){
        return basketService.rejectBasket(id);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<List<String>> acceptBasket(@PathVariable Integer id){
        return basketService.acceptBasket(id);
    }

    @PostMapping
    public ResponseEntity<BasketDto> addBasket(@RequestBody BasketDto basketDto){
        return basketService.addBasket(BasketDto.fromBasketDto(basketDto));
    }
}
