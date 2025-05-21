package com.shop.prod.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.prod.dtos.ProductDto;
import com.shop.prod.dtos.ProductWithImageDto;
import com.shop.prod.models.ProductCategories;
import com.shop.prod.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer id){
        return productService.getProduct(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable ProductCategories category){
        return productService.getAllProductsByCategory(category);
    }

    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ProductDto> addProduct(
            @RequestPart("image") MultipartFile image,
            @RequestPart("product") String productJson
    ) throws JsonProcessingException {
        ProductDto productDto = new ObjectMapper().readValue(productJson, ProductDto.class);
        return productService.addProduct(
                image,
                ProductDto.fromProductDto(productDto
                )
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductWithImageDto productWithImage
    )
    {
        return productService.updateProduct(
                id,
                productWithImage.getImage(),
                productWithImage.getProduct()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }

}
