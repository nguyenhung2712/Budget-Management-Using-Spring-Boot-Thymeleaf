package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Category;
import com.nguyenhung.bta.repository.CategoryRepository;
import com.nguyenhung.bta.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<Category> getAllCategories() {
		return this.categoryRepo.findAll();
	}

	@Override
	public Category saveCategory(Category category) {
		return this.categoryRepo.save(category);
	}

	@Override
	public void removeCategory(Long id) {
		this.categoryRepo.deleteById(id);
	}

	@Override
	public Category getCategoryById(Long id) {
		Optional<Category> optional = this.categoryRepo.findById(id);
		Category category = null;
		if(optional.isPresent()) {
			category = optional.get();
		} else {
			throw new RuntimeException("Category not found for id: " + id);
		}
		return category;
	}

	@Override
	public Category getByName(String name) {
		return this.categoryRepo.findByName(name);
	}

}
