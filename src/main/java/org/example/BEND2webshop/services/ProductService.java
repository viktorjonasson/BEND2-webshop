package org.example.BEND2webshop.services;

import org.example.BEND2webshop.dtos.ProductDto;
import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .image(product.getImage())
                .rating(product.getRating())
                .build();
    }

    private Product mapToEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .image(productDto.getImage())
                .rating(productDto.getRating())
                .build();
    }
}
