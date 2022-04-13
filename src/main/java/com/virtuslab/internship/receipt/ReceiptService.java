package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDto;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    ReceiptGenerator receiptGenerator;
    ProductDb productDb;

    public ReceiptService(ReceiptGenerator receiptGenerator, ProductDb productDb) {
        this.receiptGenerator = receiptGenerator;
        this.productDb = productDb;
    }

    public Receipt createReceipt(BasketDto basketDto){
        Basket cart = new Basket();
        List<Product> products = retrieveProductsFromDB(basketDto);
        products.forEach(cart::addProduct);
        return receiptGenerator.generate(cart);
    }

    private List<Product> retrieveProductsFromDB(BasketDto basketDto){
        return basketDto.products()
                .stream()
                .map(productDto -> productDb.getProduct(productDto.name()))
                .collect(Collectors.toList());
    }


}
