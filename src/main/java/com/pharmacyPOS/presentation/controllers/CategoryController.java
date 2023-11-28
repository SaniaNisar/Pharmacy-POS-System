package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.Category;
import com.pharmacyPOS.service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Category getCategoryById(int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public void createCategory(Category category) {
        categoryService.createCategory(category);
    }

    public void updateCategory(Category category) {
        categoryService.updateCategory(category);
    }

    public void deleteCategory(int categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
