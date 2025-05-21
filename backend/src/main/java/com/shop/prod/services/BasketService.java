package com.shop.prod.services;

import com.shop.prod.dtos.BasketDto;
import com.shop.prod.models.Basket;
import com.shop.prod.models.BasketItem;
import com.shop.prod.models.Product;
import com.shop.prod.repositories.BasketItemRepository;
import com.shop.prod.repositories.BasketRepository;
import com.shop.prod.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    @Value("${basket.life.time}")
    private Integer basketLifetime;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BasketItemRepository basketItemRepository;

    @Autowired
    ProductService productService;

    public ResponseEntity<BasketDto> addBasket(Basket basket) {
        double totalPrice = 0.0;
        basket.setCreationTime(LocalDateTime.now());
        basket.setExpirationTime(LocalDateTime.now().plusHours(basketLifetime));

        for(BasketItem basketItem : basket.getItems()){
            if(basketItem.getQuantity() < 1){
                throw new RuntimeException("Quantity cannot be negative!");
            }
            basketItem.setBasket(basket);
            totalPrice += basketItem.getQuantity() * basketItem.getProduct().getPrice();
        }

        basket.setTotalPrice(totalPrice);

        Basket addedBasket = basketRepository.save(basket);
        return new ResponseEntity<>(
                BasketDto.fromBasket(addedBasket),
                HttpStatus.OK
        );
    }

    public ResponseEntity<String> rejectBasket(Integer id){
        Basket basket = basketRepository.getReferenceById(id);
        basketRepository.delete(basket);

        deleteEmptyProducts();
        return new ResponseEntity<>(
                "Basket with id: " + basket.getId() + " was rejected!",
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<List<String>> acceptBasket(Integer id){
        Basket basket = basketRepository.getReferenceById(id);
        List<BasketItem> itemsInBasket = basket.getItems();
        List<String> messagesList = new ArrayList<>();
        messagesList.add("Basket with id: " + basket.getId() + " was accepted!");

        if(itemsInBasket != null){
            deleteProductsQuantityFromDB(itemsInBasket, messagesList);
        }

        basketRepository.delete(basket);

        deleteEmptyProducts();
        return new ResponseEntity<>(
                messagesList,
                HttpStatus.OK
        );
    }

    public ResponseEntity<BasketDto> getBasket(Integer id){
        deleteExpiredBaskets();

        Basket basket = basketRepository.getReferenceById(id);
        return new ResponseEntity<>(
                BasketDto.fromBasket(basket),
                HttpStatus.OK
        );
    }

    public ResponseEntity<List<BasketDto>> getAllBaskets() {
        deleteExpiredBaskets();

        List<Basket> allBaskets = basketRepository.findAll();
        return new ResponseEntity<>(
                BasketDto.fromBasket(allBaskets),
                HttpStatus.OK
        );
    }

    private List<String> deleteProductsQuantityFromDB(List<BasketItem> itemsInBasket, List<String> messagesList){
        for(BasketItem basketItem : itemsInBasket){
            String nextMessage = deleteProductQuantityFromDB(basketItem.getProduct(), basketItem.getQuantity());
            messagesList.add(nextMessage);
        }

        return messagesList;
    }

    private String deleteProductQuantityFromDB(Product product, Integer quantity){
        Product productInDb = productRepository.getReferenceById(product.getId());
        Integer quantityOfProductInStore = productInDb.getQuantity();
        String message;

        if(quantityOfProductInStore < quantity){
            message = (quantity-quantityOfProductInStore) + " units of " + product.getTitle() + " were missing";
            productInDb.setQuantity(0);
        }else {
            productInDb.setQuantity(quantityOfProductInStore-quantity);
            message = productInDb.getQuantity() + " units of " + product.getTitle() + " left";
        }

        return message;
    }

    private void deleteExpiredBaskets(){
        List<Basket> allBaskets = basketRepository.findAll();

        for(Basket basket : allBaskets){
            if(basket.getExpirationTime().isBefore(LocalDateTime.now())){
                basketRepository.delete(basket);
            }
        }
    }

    private void deleteEmptyProducts(){
        List<Product> allProducts = productRepository.findAll();

        for(Product product : allProducts){
            if(product.getQuantity() <= 0){
                checkLinksAndDeleteEmptyProduct(product);
            }
        }
    }

    private void checkLinksAndDeleteEmptyProduct(Product product){
        List<BasketItem> basketItems = basketItemRepository.findByProduct(product);
        if(basketItems.isEmpty()){
            productService.deleteProduct(product.getId());
        }
    }
}
