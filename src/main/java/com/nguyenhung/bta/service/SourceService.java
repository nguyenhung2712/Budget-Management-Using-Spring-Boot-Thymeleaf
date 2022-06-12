package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Source;

public interface SourceService {
	List<Source> getAllSources();
	Source getSourceById(Long id);
	Source saveSource(Source source);
	void removeSource(Long id);
	Source getByName(String name);
}
