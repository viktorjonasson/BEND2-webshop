package org.example.BEND2webshop.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto {

    private Long id;
    private LocalDateTime purchaseDate;

    private Long productId;
    private String productTitle;
    private Double productPrice;

    private UUID userId;
    private String username;
}
