package com.nguyenhung.bta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenhung.bta.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
