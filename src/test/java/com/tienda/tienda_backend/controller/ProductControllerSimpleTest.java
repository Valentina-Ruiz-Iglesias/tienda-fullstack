package com.tienda.tienda_backend.controller;

import com.tienda.tienda_backend.entity.Product;
import com.tienda.tienda_backend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductControllerSimpleTest {

    @Test
    void getAll_returnsOkWithProducts() {
        ProductService productService = Mockito.mock(ProductService.class);
        ProductController controller = new ProductController(productService);

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Torta Chocolate");
        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Torta Frutilla");

        Mockito.when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        ResponseEntity<List<Product>> response = controller.getAll();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Torta Chocolate");

        verify(productService, times(1)).getAllProducts();
    }
}
