package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Income;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.repository.IncomeRepository;
import com.nguyenhung.bta.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepo;
	
	@Override
	public List<Income> getAllIncomes() {
		return this.incomeRepo.findAll();
	}

	@Override
	public Income saveIncome(Income income) {
		return this.incomeRepo.save(income);
	}

	@Override
	public void removeIncome(Long id) {
		this.incomeRepo.deleteById(id);
	}

	@Override
	public Income getIncomeById(Long income_id) {
		Optional<Income> optional = this.incomeRepo.findById(income_id);
		Income income = null;
		if(optional.isPresent()) {
			income = optional.get();
		} else {
			throw new RuntimeException("Income not found for id: " + income_id);
		}
		return income;
	}

	@Override
	public List<Income> getIncomesByUser(User user) {
		return this.incomeRepo.findByUser(user);
	}

	@Override
	public Float getIncomeTotal(Long id, int month, int year) {
		return this.incomeRepo.findIncomeTotal(id, month, year);
	}

	@Override
	public Float getIncomeWithDay(int day, int month, int year, Long id) {
		return this.incomeRepo.findIncomeWithDay(day, month, year, id);
	}

	@Override
	public Float getIncomeWithSource(String source, Long id, int month, int year) {
		return this.incomeRepo.findIncomeWithSource(source, id, month, year);
	}

	@Override
	public List<Income> getIncomeBySource(String source, Long id, int month, int year) {
		return this.incomeRepo.findIncomeBySource(source, id, month, year);
	}

	@Override
	public List<Income> getIncomeByDay(int day, int month, int year, Long id) {
		return this.incomeRepo.findIncomeByDay(day, month, year, id);
	}

	@Override
	public List<Income> getIncomeByMonth(int month, int year, Long id) {
		return this.incomeRepo.findIncomeByMonth(month, year, id);
	}

	@Override
	public Float getIncomeWithSourceInDay(String source, Long id, int day, int month, int year) {
		return this.incomeRepo.findIncomeWithSourceInDay(source, id, day, month, year);
	}

}
