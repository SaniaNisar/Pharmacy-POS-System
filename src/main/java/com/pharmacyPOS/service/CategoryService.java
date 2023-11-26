package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.CategoryDao;
import com.pharmacyPOS.data.entities.Category;

import java.util.List;

public class CategoryService
{
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao)
    {
        this.categoryDao=categoryDao;
    }

    public List<Category> getAllCategories()
    {
        return (categoryDao.getAllCategories());
    }
}
