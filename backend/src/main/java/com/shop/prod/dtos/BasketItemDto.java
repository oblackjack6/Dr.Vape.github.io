package com.shop.prod.dtos;

import com.shop.prod.models.Basket;
import com.shop.prod.models.BasketItem;
import com.shop.prod.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class BasketItemDto {
    private Integer id;

    private ProductDto product;

    private Integer quantity;

    public static BasketItemDto fromBasketItem(BasketItem basketItem){
        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setId(basketItem.getId());
        basketItemDto.setProduct(ProductDto.fromProduct(basketItem.getProduct()));
        basketItemDto.setQuantity(basketItem.getQuantity());

        return basketItemDto;
    }

    public static BasketItem fromBasketItemDto(BasketItemDto basketItemDto, Basket basket){
        BasketItem basketItem = new BasketItem();
        basketItem.setId(basketItemDto.getId());
        basketItem.setProduct(ProductDto.fromProductDto(basketItemDto.getProduct()));
        basketItem.setQuantity(basketItemDto.getQuantity());
        basketItem.setBasket(basket);

        return basketItem;
    }

    public static List<BasketItemDto> fromBasketItem(List<BasketItem> basketItemList){
        return basketItemList.stream().map(BasketItemDto::fromBasketItem).toList();
    }

    public static List<BasketItem > fromBasketItemDto(List<BasketItemDto> basketItemDtoList, Basket basket){
        return basketItemDtoList.stream().map(basketItem -> BasketItemDto.fromBasketItemDto(basketItem, basket)).toList();
    }
}
