package com.virtuslab.internship.product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductDb {

    private final Set<Product> products;

    public ProductDb() {
        products = Stream.of(
                new Product("Apple", Product.Type.FRUITS, new BigDecimal(2)),
                new Product("Orange", Product.Type.FRUITS, new BigDecimal(5)),
                new Product("Banana", Product.Type.FRUITS, new BigDecimal("4.4")),
                new Product("Potato", Product.Type.VEGETABLES, new BigDecimal("1.2")),
                new Product("Tomato", Product.Type.VEGETABLES, new BigDecimal(7)),
                new Product("Onion", Product.Type.VEGETABLES, new BigDecimal("1.7")),
                new Product("Milk", Product.Type.DAIRY, new BigDecimal("2.7")),
                new Product("Cheese", Product.Type.DAIRY, new BigDecimal("20.5")),
                new Product("Butter", Product.Type.DAIRY, new BigDecimal(7)),
                new Product("Pork", Product.Type.MEAT, new BigDecimal(16)),
                new Product("Steak", Product.Type.MEAT, new BigDecimal(50)),
                new Product("Bread", Product.Type.GRAINS, new BigDecimal(5)),
                new Product("Cereals", Product.Type.GRAINS,new BigDecimal(8))
        ).collect(Collectors.toSet());
    }

    public Product getProduct(String productName) {
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> productName.equals(product.name()))
                .findFirst();
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        else throw new NoSuchProductException();
    }
}
