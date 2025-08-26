package org.example.BEND2webshop.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Rating {
    public double rate;
    public int count;
}