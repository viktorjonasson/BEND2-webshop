package org.example.BEND2webshop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Root;
import org.example.BEND2webshop.dtos.ProductDto;
import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductImportService {

    @Autowired
    ProductRepository productRepository;

    public ProductImportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void fetchAndSaveProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("https://fakestoreapi.com/products");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

            ProductDto[] products = objectMapper.readValue(sb.toString(), ProductDto[].class);

            List<ProductDto> productDtoList = Arrays.asList(products);

            productDtoList.forEach(System.out::println);

            //ToDo: Map Root objects to Product entities via DTOs

            //Call method to map products to entities

            //Save products to database
            //productRepository.saveAll(productList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
