package com.shop.prod.dtos;
import com.shop.prod.models.Product;
import com.shop.prod.models.ProductCategories;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Integer id;
    private String imgUrl;
    private String title;
    private ProductCategories category;
    private Double price;
    private Integer quantity;

    public static ProductDto fromProduct(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setImgUrl(product.getImgUrl());
        productDto.setTitle(product.getTitle());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());

        return productDto;
    }

    public static Product fromProductDto(ProductDto productDto){
        Product product = new Product();

        product.setId(productDto.getId());
        product.setImgUrl(productDto.getImgUrl());
        product.setTitle(productDto.getTitle());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        return product;
    }

    public static List<ProductDto> fromProduct(List<Product> productsList){
        return productsList.stream().map(ProductDto::fromProduct).toList();
    }

    public static List<Product> fromProductDto(List<ProductDto> productsDtoList){
        return productsDtoList.stream().map(ProductDto::fromProductDto).toList();
    }
}
