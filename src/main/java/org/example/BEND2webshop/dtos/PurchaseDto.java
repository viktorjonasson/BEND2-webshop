package org.example.BEND2webshop.dtos;

public class PurchaseDto {

    private final Long id;
    private final Long productId;
    private final Integer quantity;

    public PurchaseDto(final Long id, final Long productId, final Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId(){
            return productId;
    }

    public Integer getQuantity() {
    return quantity;
    }
}
