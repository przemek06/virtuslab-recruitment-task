package com.virtuslab.internship.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDto;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.virtuslab.internship.receipt.Constants.BASKET_NOT_VALID_RESPONSE;
import static com.virtuslab.internship.receipt.Constants.NO_SUCH_PRODUCT_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReceiptGenerator receiptGenerator;

    @Autowired
    ProductDb productDb;

    private final String uri = "/api/receipt";

    @Test
    void shouldReturnProperReceiptWhenCorrectBasketData() throws Exception {
        Product product1 = productDb.getProduct("Bread");
        Product product2 = productDb.getProduct("Cheese");
        Product product3 = productDb.getProduct("Steak");

        Basket basket = new Basket();

        basket.addProduct(product1);
        basket.addProduct(product1);
        basket.addProduct(product2);
        basket.addProduct(product3);

        Receipt expectedReceipt = receiptGenerator.generate(basket);

        ProductDto productDto1 = new ProductDto("Bread");
        ProductDto productDto2 = new ProductDto("Bread");
        ProductDto productDto3 = new ProductDto("Cheese");
        ProductDto productDto4 = new ProductDto("Steak");

        BasketDto basketDto = new BasketDto(Arrays.asList(productDto1, productDto2, productDto3, productDto4));

        ObjectMapper objectMapper = new ObjectMapper();
        String basketJson = objectMapper.writeValueAsString(basketDto);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(basketJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);

        String jsonContent = result.getResponse().getContentAsString();
        Receipt responseReceipt = objectMapper.readValue(jsonContent, Receipt.class);
        assertEquals(expectedReceipt, responseReceipt);

    }

    @Test
    void shouldReturn404NotFoundResponseWhenNotExistingProductsInBasket() throws Exception {
        ProductDto productDto1 = new ProductDto("Bread");
        ProductDto productDto2 = new ProductDto("Lasagne");
        ProductDto productDto3 = new ProductDto("Cheese");

        BasketDto basketDto = new BasketDto(Arrays.asList(productDto1, productDto2, productDto3));

        ObjectMapper objectMapper = new ObjectMapper();
        String basketJson = objectMapper.writeValueAsString(basketDto);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(basketJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(404, status);
        String content = result.getResponse().getContentAsString();
        assertEquals(String.format(NO_SUCH_PRODUCT_RESPONSE, "Lasagne"), content);
    }

    @Test
    void shouldReturn400ResponseWhenBasketNotValid() throws Exception {

        String basketJson = "{\"products\": [{\"named\": \"Bread\"}]}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(basketJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(400, status);
        String content = result.getResponse().getContentAsString();
        assertEquals(BASKET_NOT_VALID_RESPONSE, content);
    }
}
