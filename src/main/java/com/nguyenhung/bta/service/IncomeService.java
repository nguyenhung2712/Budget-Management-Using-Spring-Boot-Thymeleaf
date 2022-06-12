package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Income;
import com.nguyenhung.bta.entity.User;

public interface IncomeService {
	List<Income> getAllIncomes();
	Income getIncomeById(Long income_id);
	Income saveIncome(Income income);
	void removeIncome(Long id);
	List<Income> getIncomesByUser(User user);
	Float getIncomeTotal(Long id, int month, int year);
	Float getIncomeWithDay(int day, int month, int year, Long id);
	List<Income> getIncomeByDay(int day, int month, int year, Long id);
	List<Income> getIncomeByMonth(int month, int year, Long id);
	Float getIncomeWithSource(String source, Long id, int month, int year);
	Float getIncomeWithSourceInDay(String source, Long id, int day, int month, int year);
	List<Income> getIncomeBySource(String source, Long id, int month, int year);
}
