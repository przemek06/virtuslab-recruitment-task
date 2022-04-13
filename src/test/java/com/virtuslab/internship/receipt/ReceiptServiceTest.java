package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDto;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReceiptServiceTest {

    @Autowired
    ReceiptService receiptService;
    @Autowired
    ReceiptGenerator receiptGenerator;
    @Autowired
    ProductDb productDb;
    @Test
    void shouldCreateReceiptFromBasketDto(){
        ProductDto bread = new ProductDto("Bread");
        ProductDto cheese = new ProductDto("Cheese");
        BasketDto basketDto = new BasketDto(Arrays.asList(cheese, bread, bread, cheese, cheese));
        Receipt receipt = receiptService.createReceipt(basketDto);

        Product breadFromDB = productDb.getProduct("Bread");
        Product cheeseFromDB = productDb.getProduct("Cheese");
        Basket basket = new Basket();
        basket.addProduct(breadFromDB);
        basket.addProduct(breadFromDB);
        basket.addProduct(cheeseFromDB);
        basket.addProduct(cheeseFromDB);
        basket.addProduct(cheeseFromDB);
        Receipt expectedReceipt = receiptGenerator.generate(basket);

        assertEquals(expectedReceipt.totalPrice(), receipt.totalPrice());
        assertEquals(expectedReceipt.discounts(), receipt.discounts());
        assertEquals(expectedReceipt.entries(), expectedReceipt.entries());
    }
}
