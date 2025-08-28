package org.example.BEND2webshop.exceptions;

public class ProductNotFoundException extends RuntimeException {

    private final Long productId;

    public ProductNotFoundException(long productId)  {
        super("Product with ID " + productId + " not found.");
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
