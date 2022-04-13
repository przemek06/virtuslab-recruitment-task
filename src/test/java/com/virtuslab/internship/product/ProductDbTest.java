package com.virtuslab.internship.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductDbTest {

    ProductDb productDb = new ProductDb();

    @Test
    void shouldReturnProperProductWhenItExists(){
        Product product = productDb.getProduct("Bread");
        Product expectedProduct = new Product("Bread", Product.Type.GRAINS, new BigDecimal(5));
        assertEquals(expectedProduct, product);
    }

    @Test
    void shouldThrowNoSuchProductExceptionWithProperStringWhenProductNotExisting(){
        Exception exception = assertThrows(Exception.class,
                ()->productDb.getProduct("Lasagne"));

        assertEquals(NoSuchProductException.class, exception.getClass());
        String expectedNotExistingProduct = "Lasagne";
        assertEquals(expectedNotExistingProduct, ((NoSuchProductException)exception).getNotExistingProduct());
    }
}
