package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Category;

public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategoryById(Long id);
	Category saveCategory(Category category);
	Category getByName(String name);
	void removeCategory(Long id);
}
