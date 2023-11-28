package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.CategoryDao;
import com.pharmacyPOS.data.entities.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category getCategoryById(int categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public void createCategory(Category category) {
        categoryDao.createCategory(category);
    }

    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    public void deleteCategory(int categoryId) {
        categoryDao.deleteCategory(categoryId);
    }
}
