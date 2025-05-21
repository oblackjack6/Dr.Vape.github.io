package com.shop.prod.dtos;

import com.shop.prod.models.Basket;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BasketDto {
    private Integer id;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;

    private List<BasketItemDto> items = new ArrayList<>();

    private String message;
    private String contactDetails;

    private Double totalPrice;

    public static BasketDto fromBasket(Basket basket) {
        BasketDto basketDto = new BasketDto();
        basketDto.setId(basket.getId());
        basketDto.setCreationTime(basket.getCreationTime());
        basketDto.setExpirationTime(basket.getExpirationTime());
        basketDto.setItems(BasketItemDto.fromBasketItem(basket.getItems()));
        basketDto.setMessage(basket.getMessage());
        basketDto.setContactDetails(basket.getContactDetails());
        basketDto.setTotalPrice(basket.getTotalPrice());

        return basketDto;
    }

    public static Basket fromBasketDto(BasketDto basketDto) {
        Basket basket = new Basket();
        basket.setId(basketDto.getId());
        basket.setCreationTime(basketDto.getCreationTime());
        basket.setExpirationTime(basketDto.getExpirationTime());
        basket.setItems(BasketItemDto.fromBasketItemDto(basketDto.getItems(), basket));
        basket.setMessage(basketDto.getMessage());
        basket.setContactDetails(basketDto.getContactDetails());
        basket.setTotalPrice(basketDto.getTotalPrice());

        return basket;
    }

    public static List<BasketDto> fromBasket(List<Basket> basketList){
        return basketList.stream().map(BasketDto::fromBasket).toList();
    }

    public static List<Basket> fromBasketDto(List<BasketDto> basketDtoList){
        return basketDtoList.stream().map(BasketDto::fromBasketDto).toList();
    }
}
