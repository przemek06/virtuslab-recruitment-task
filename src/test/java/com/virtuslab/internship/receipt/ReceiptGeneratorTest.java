package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReceiptGeneratorTest {

    @Test
    void shouldGenerateReceiptForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price()).add(apple.price());

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }


    @Test
    void shouldGenerateReceiptWithTwoEntriesFromBasketWithNoDiscounts(){
        var productDb = new ProductDb();
        var cart = new Basket();
        var bread1 = productDb.getProduct("Bread");
        var bread2 = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        cart.addProduct(bread1);
        cart.addProduct(bread2);
        cart.addProduct(apple);
        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);
        var expectedTotalPrice = bread1.price().multiply(BigDecimal.valueOf(2))
                .add(apple.price());
        // Then
        assertNotNull(receipt);
        assertEquals(2, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWith15PercentDiscountFromBasket(){
        var productDb = new ProductDb();
        var cart = new Basket();

        var bread1 = productDb.getProduct("Bread");
        var bread2 = productDb.getProduct("Bread");
        var cereal = productDb.getProduct("Cereals");
        var cheese = productDb.getProduct("Cheese");

        var expectedTotalPrice = bread1.price().multiply(BigDecimal.valueOf(2))
                .add(cereal.price().multiply(BigDecimal.valueOf(3)))
                .add(cheese.price())
                .multiply(BigDecimal.valueOf(0.85));

        cart.addProduct(bread1);
        cart.addProduct(bread2);
        cart.addProduct(cereal);
        cart.addProduct(cereal);
        cart.addProduct(cereal);
        cart.addProduct(cheese);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);

        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWith10PercentDiscountFromBasket(){
        var productDb = new ProductDb();
        var cart = new Basket();

        var bread = productDb.getProduct("Bread");
        var cereal = productDb.getProduct("Cereals");
        var cheese = productDb.getProduct("Cheese");
        var expectedTotalPrice = bread.price()
                .add(cereal.price())
                .add(cheese.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.9));
        cart.addProduct(bread);
        cart.addProduct(cereal);
        cart.addProduct(cheese);
        cart.addProduct(cheese);
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);

        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWithBothDiscountsFromBasket(){
        var productDb = new ProductDb();
        var cart = new Basket();

        var bread1 = productDb.getProduct("Bread");
        var bread2 = productDb.getProduct("Bread");
        var cereal = productDb.getProduct("Cereals");
        var cheese = productDb.getProduct("Cheese");

        var expectedTotalPrice = bread1.price().multiply(BigDecimal.valueOf(2))
                .add(cereal.price()).add(cheese.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.9*0.85));

        cart.addProduct(bread1);
        cart.addProduct(bread2);
        cart.addProduct(cereal);
        cart.addProduct(cheese);
        cart.addProduct(cheese);

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(cart);

        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
    }
}
