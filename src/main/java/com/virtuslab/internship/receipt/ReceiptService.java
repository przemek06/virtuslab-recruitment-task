package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.basket.BasketDto;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible processing basket information sent by a client and creating a receipt based on that information.
 */
@Service
public class ReceiptService {

    ReceiptGenerator receiptGenerator;
    ProductDb productDb;

    public ReceiptService(ReceiptGenerator receiptGenerator, ProductDb productDb) {
        this.receiptGenerator = receiptGenerator;
        this.productDb = productDb;
    }

    /**
     * Retrieve products specified in basket information from database and create a receipt based on these products.
     *
     * @param basketDto information needed to retrieve products from database.
     * @return receipt created based on products in database.
     */
    public Receipt createReceipt(BasketDto basketDto){
        Basket cart = new Basket();
        List<Product> products = retrieveProductsFromDB(basketDto);
        products.forEach(cart::addProduct);
        return receiptGenerator.generate(cart);
    }

    /**
     * Using the names specified in basket's products DTOs, look up every product and make a list of them.
     *
     * @param basketDto information containing list of product DTOs.
     * @return list of products.
     */
    private List<Product> retrieveProductsFromDB(BasketDto basketDto){
        return basketDto.products()
                .stream()
                .map(productDto -> productDb.getProduct(productDto.name()))
                .collect(Collectors.toList());
    }


}
