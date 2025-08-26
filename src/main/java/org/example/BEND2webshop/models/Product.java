package org.example.BEND2webshop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Long id;

    private String title;
    private Double price;

    @Column(length = 3000)
    private String description;
    private String category;
    private String image;

    @Embedded
    public Rating rating;
}
