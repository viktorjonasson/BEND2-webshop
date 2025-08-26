package org.example.BEND2webshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Purchase {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;
    private Long buyerId;

    private Integer quantity;

}
