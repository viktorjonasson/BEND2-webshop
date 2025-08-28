package org.example.BEND2webshop.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long productId)  {
        super("Product with ID " + productId + " not found.");
    }
}
