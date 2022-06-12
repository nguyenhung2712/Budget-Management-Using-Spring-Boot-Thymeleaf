package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Source;
import com.nguyenhung.bta.repository.SourceRepository;
import com.nguyenhung.bta.service.SourceService;

@Service
public class SourceServiceImpl implements SourceService {

	@Autowired
	private SourceRepository sourceRepo;
	
	@Override
	public List<Source> getAllSources() {
		return this.sourceRepo.findAll();
	}

	@Override
	public Source saveSource(Source source) {
		return this.sourceRepo.save(source);
	}

	@Override
	public void removeSource(Long id) {
		this.sourceRepo.deleteById(id);
	}

	@Override
	public Source getSourceById(Long id) {
		Optional<Source> optional = this.sourceRepo.findById(id);
		Source source = null;
		if(optional.isPresent()) {
			source = optional.get();
		} else {
			throw new RuntimeException("Source not found for id: " + id);
		}
		return source;
	}

	@Override
	public Source getByName(String name) {
		return this.sourceRepo.findByName(name);
	}

}
