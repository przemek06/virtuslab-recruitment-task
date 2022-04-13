package com.virtuslab.internship.product;

/**
 * Exception which is thrown on a try to access not existing product in product database.
 */
public class NoSuchProductException extends RuntimeException {
    final String notExistingProduct;
    public NoSuchProductException(String notExistingProduct) {
        super();
        this.notExistingProduct=notExistingProduct;
    }

    public String getNotExistingProduct() {
        return notExistingProduct;
    }
}
