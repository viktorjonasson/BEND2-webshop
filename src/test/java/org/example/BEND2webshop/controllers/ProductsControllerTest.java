package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.dtos.ProductDto;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.Rating;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductsController.class)
@ActiveProfiles("test")
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ConcreteUserDetails createMockUserDetails() {
        AppUser mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("mocker");
        mockUser.setPassword("mockpassword");
        mockUser.setEnabled(true);
        mockUser.setRoles(List.of(new UserRole(UUID.randomUUID(), "USER")));

        return new ConcreteUserDetails(mockUser);
    }

    @Test
    void shouldDisplayProductsPageWithAllProducts() throws Exception {
        ConcreteUserDetails userDetails = createMockUserDetails();

        Rating rating1 = new Rating();
        rating1.setRate(4.5);
        rating1.setCount(120);

        Rating rating2 = new Rating();
        rating2.setRate(3.8);
        rating2.setCount(85);

        ProductDto product1 = ProductDto.builder()
                .id(1L)
                .title("Product 1")
                .price(99.99)
                .description("Description")
                .category("General")
                .image("")
                .rating(rating1)
                .build();

        ProductDto product2 = ProductDto.builder()
                .id(2L)
                .title("Product 2")
                .price(149.99)
                .description("Description")
                .category("General")
                .image("")
                .rating(rating2)
                .build();

        List<ProductDto> mockProducts = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(mockProducts);

        mockMvc.perform(get("/products")
                        .with(csrf())
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void shouldDisplayProductsPageWithEmptyListWhenNoProducts() throws Exception {
        List<ProductDto> emptyProducts = List.of();
        ConcreteUserDetails userDetails = createMockUserDetails();

        when(productService.getAllProducts()).thenReturn(emptyProducts);

        mockMvc.perform(get("/products")
                        .with(csrf())
                        .with(user(userDetails))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(0)))
                .andExpect(model().attribute("products", empty()));

        verify(productService, times(1)).getAllProducts();
    }
}
