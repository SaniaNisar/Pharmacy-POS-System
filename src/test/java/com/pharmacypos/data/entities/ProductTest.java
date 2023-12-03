package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

class ProductTest {

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        int productId = 1;
        String name = "TestProduct";
        String description = "A test product";
        double price = 19.99;
        int categoryId = 101;
        Date expirationDate = new Date();
        Product product = new Product();

        // Act
        product.setProductId(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategoryId(categoryId);
        product.setExpirationDate(expirationDate);

        // Assert
        assertEquals(productId, product.getProductId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(categoryId, product.getCategoryId());
        assertEquals(expirationDate, product.getExpirationDate());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        int productId = 1;
        String name = "TestProduct";
        String description = "A test product";
        double price = 19.99;
        int categoryId = 101;
        Date expirationDate = new Date();
        Product product = new Product(productId, name, description, price, categoryId, expirationDate);

        // Act
        String result = product.toString();

        // Assert
        assertTrue(result.contains("productId=" + productId));
        assertTrue(result.contains("name='" + name + "'"));
        assertTrue(result.contains("description='" + description + "'"));
        assertTrue(result.contains("price=" + price));
        assertTrue(result.contains("categoryId=" + categoryId));
        assertTrue(result.contains("expirationDate=" + expirationDate));
    }
}
