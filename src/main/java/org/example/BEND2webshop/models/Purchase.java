package org.example.BEND2webshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Purchase {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long productId;

    private Integer quantity;
    private LocalDateTime orderTime = LocalDateTime.now();

}
