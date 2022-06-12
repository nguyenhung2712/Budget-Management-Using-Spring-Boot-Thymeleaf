package com.nguyenhung.bta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nguyenhung.bta.entity.Source;

public interface SourceRepository  extends JpaRepository<Source, Long> {
	Source findByName(String name);
}
