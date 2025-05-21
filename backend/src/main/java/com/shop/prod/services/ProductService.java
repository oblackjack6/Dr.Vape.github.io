package com.shop.prod.services;

import com.shop.prod.dtos.ProductDto;
import com.shop.prod.models.Product;
import com.shop.prod.models.ProductCategories;
import com.shop.prod.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    public ResponseEntity<ProductDto> addProduct(
            MultipartFile image,
            Product newProductData
    ){
        if(newProductData.getQuantity() != null){
            if(newProductData.getQuantity() <= 0){
                throw new RuntimeException("Product quantity can't be smaller than 0!");
            }
        }

        Product productInRepo = productRepository.findByTitle(newProductData.getTitle());
        if(productInRepo != null){
            if(productInRepo.getQuantity() == 0){
                return updateProduct(productInRepo.getId(), image, newProductData);
            } else {
                return new ResponseEntity<>(ProductDto.fromProduct(productInRepo),
                        HttpStatus.OK);
            }
        }

        if(image != null){
            newProductData.setImgUrl(
                    imageService.uploadImage(
                            image,
                            newProductData.getImgUrl()
                    ).getBody()
            );
        }

        Product addedProduct = productRepository.save(newProductData);

        return new ResponseEntity<>(ProductDto.fromProduct(addedProduct),
                HttpStatus.OK);
    }

    public ResponseEntity<ProductDto> updateProduct(
            Integer id,
            MultipartFile image,
            Product newProductData
    ){
        if(newProductData.getQuantity() != null){
            if(newProductData.getQuantity() <= 0){
                throw new RuntimeException("Product quantity can't be smaller than 0!");
            }
        }

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new RuntimeException("Product with id: " + id + " no found!");
        }

        Product productInRepo = productOptional.get();

        if(image != null){
            newProductData.setImgUrl(
                    imageService.uploadImage(
                            image,
                            productInRepo.getImgUrl()
                    ).getBody()
            );
        }

        productInRepo.update(newProductData);

        Product updatedProduct = productRepository.save(productInRepo);

        return new ResponseEntity<>(ProductDto.fromProduct(updatedProduct),
                HttpStatus.OK);
    }

    public ResponseEntity<ProductDto> getProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new RuntimeException("Entity with id: " + id + " not found!");
        } else if(product.get().getQuantity() <= 0){
            throw new RuntimeException("Entity with id: " + id + " not found!");
        }

        return new ResponseEntity<>(ProductDto.fromProduct(product.get()),
                HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for(Product product : allProducts){
            if(product.getQuantity() > 0){
                filteredProducts.add(product);
            }
        }

        return new ResponseEntity<>(
                ProductDto.fromProduct(filteredProducts),
                HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(ProductCategories category) {
        List<Product> allProducts = productRepository.findByCategory(category);

        return new ResponseEntity<>(
                ProductDto.fromProduct(allProducts),
                HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new RuntimeException("Product with id: " + id + " doesn't exist!");
        }
        String imgName = productOptional.get().getImgUrl();
        productRepository.deleteById(id);

        productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            throw new RuntimeException("Product with id: " + id + " wasn't deleted!");
        }

        if(imgName != null){
            imageService.deleteImage(imgName);
        }

        return new ResponseEntity<>("Product with id: " + id + " was successfully deleted",
                HttpStatus.OK);
    }
}
