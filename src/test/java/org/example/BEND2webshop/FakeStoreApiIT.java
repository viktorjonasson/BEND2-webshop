package org.example.BEND2webshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FakeStoreApiIT {

    @Test
    void shouldReturnJsonFromExternalUrl(){
        System.out.println("Testing JSON");
        String url = "https://fakestoreapi.com/products";
        TestRestTemplate restTemplate = new TestRestTemplate();
        String json = restTemplate.getForObject(url, String.class);
        assertNotNull(json.contains("title"));

    }
}
