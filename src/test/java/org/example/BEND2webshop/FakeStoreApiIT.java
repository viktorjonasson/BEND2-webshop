package org.example.BEND2webshop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class FakeStoreApiIT {

    @Test
    void shouldReturnNonEmptyProductList() throws Exception {
        String url = "https://fakestoreapi.com/products";
        TestRestTemplate restTemplate = new TestRestTemplate();
        String json = restTemplate.getForObject(url, String.class);
        assertNotNull(json);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        assertTrue(root.isArray(), "Response should be a JSON array");
        assertFalse(root.isEmpty(), "Product list should not be empty");
    }

    @Test
    void shouldReturnValidProductById() throws Exception {
        String url = "https://fakestoreapi.com/products/1";
        TestRestTemplate restTemplate = new TestRestTemplate();
        String json = restTemplate.getForObject(url, String.class);
        assertNotNull(json);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode product = mapper.readTree(json);
        boolean hasIntId = product.has("id") && product.get("id").isInt();
        boolean hasNonEmptyTitle = product.has("title") && product.get("title").isTextual() && !product.get("title").asText().isEmpty();
        boolean hasDoublePrice = product.has("price") && (product.get("price").isDouble() || product.get("price").isInt());
        assertTrue(hasIntId, "Product must have an integer id");
        assertTrue(hasNonEmptyTitle, "Product must have a non-empty string title");
        assertTrue(hasDoublePrice, "Product must have a double or integer price");
    }
}
