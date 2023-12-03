package com.pharmacyPOS.data.entities;

import com.pharmacyPOS.data.entities.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() {
        category = new Category(1, "TestCategory", "TestDescription");
    }

    @Test
    public void testGetCategoryId() {
        assertEquals(1, category.getCategoryId());
    }

    @Test
    public void testGetName() {
        assertEquals("TestCategory", category.getName());
    }

    @Test
    public void testSetName() {
        category.setName("UpdatedCategory");
        assertEquals("UpdatedCategory", category.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("TestDescription", category.getDescription());
    }

    @Test
    public void testSetDescription() {
        category.setDescription("UpdatedDescription");
        assertEquals("UpdatedDescription", category.getDescription());
    }

    @Test
    public void testToString() {
        String expectedToString = "Category{" +
                "categoryId=1" +
                ", name='TestCategory'" +
                ", description='TestDescription'" +
                '}';
        assertEquals(expectedToString, category.toString());
    }
}

