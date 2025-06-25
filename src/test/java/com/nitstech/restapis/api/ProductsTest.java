package com.nitstech.restapis.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitstech.restapis.domain.Product;
import com.nitstech.restapis.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Products.class)
class ProductsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = Product.builder().id(1L).name("P1").description("D1").price(new BigDecimal("10")).build();
        Product p2 = Product.builder().id(2L).name("P2").price(new BigDecimal("20")).build();

        given(repository.findAll()).willReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product toSave = Product.builder().name("Laptop").description("High-performance laptop").price(new BigDecimal("999.99")).build();

        Product saved = Product.builder().id(1L).name("Laptop").description("High-performance laptop").price(new BigDecimal("999.99")).build();

        given(repository.save(any(Product.class))).willReturn(saved);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("High-performance laptop"))
                .andExpect(jsonPath("$.price").value(999.99));
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        Product existing = Product.builder().id(1L).name("Laptop").description("Old").price(new BigDecimal("999.99")).build();

        Product updated = Product.builder().id(1L).name("Laptop").description("Updated").price(new BigDecimal("1099.99")).build();

        given(repository.findById(1L)).willReturn(Optional.of(existing));
        given(repository.save(any(Product.class))).willReturn(updated);

        mockMvc.perform(put("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        Product product = Product.builder().name("Laptop").description("Desc").price(new BigDecimal("10")).build();

        given(repository.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        Product toDelete = Product.builder().id(1L).name("X").description("Y").price(new BigDecimal("0.1")).build();

        given(repository.findById(1L)).willReturn(Optional.of(toDelete));

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        given(repository.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProductValidation() throws Exception {
        Product invalid = Product.builder().name("").description("").price(new BigDecimal("-1")).build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists());
    }
}
