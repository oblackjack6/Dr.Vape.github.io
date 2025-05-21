package com.shop.prod.dtos;

import com.shop.prod.models.Product;
import com.shop.prod.models.ProductCategories;
import org.springframework.web.multipart.MultipartFile;

public class ProductWithImageDto {
    private Integer id;
    private MultipartFile imgFile;
    private String imgUrl;
    private String title;
    private ProductCategories category;
    private Double price;
    private Integer quantity;

    public MultipartFile getImage(){
        return imgFile;
    }

    public Product getProduct(){
        Product product = new Product();
        product.setId(this.id);
        product.setImgUrl(this.imgUrl);
        product.setTitle(this.title);
        product.setCategory(this.category);
        product.setPrice(this.price);
        product.setQuantity(this.quantity);

        return product;
    }
}
