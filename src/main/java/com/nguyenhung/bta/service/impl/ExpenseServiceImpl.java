package com.nguyenhung.bta.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Expense;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.repository.ExpenseRepository;
import com.nguyenhung.bta.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepo;
	
	@Override
	public List<Expense> getAllExpenses() {
		return this.expenseRepo.findAll();
	}

	@Override
	public Expense saveExpense(Expense expense) {
		return this.expenseRepo.save(expense);
	}

	@Override
	public void removeExpense(Long id) {
		this.expenseRepo.deleteById(id);
	}

	@Override
	public Expense getExpenseById(Long expense_id) {
		Optional<Expense> optional = this.expenseRepo.findById(expense_id);
		Expense expense = null;
		if(optional.isPresent()) {
			expense = optional.get();
		} else {
			throw new RuntimeException("Expense not found for id: " + expense_id);
		}
		return expense;
	}

	@Override
	public List<Expense> getExpensesByUser(User user) {
		return this.expenseRepo.findByUser(user);
	}

	@Override
	public Float getExpenseTotal(Long id, int month, int year) {
		return this.expenseRepo.findExpenseTotal(id, month, year);
	}

	@Override
	public int getLastMonth(String date) {
		return this.expenseRepo.findLastMonth(date);
	}

	@Override
	public int getYearOfLastMonth(String date) {
		return this.expenseRepo.findYearOfLastMonth(date);
	}

	@Override
	public List<Integer> getDayList(int month, int year) {
		return this.expenseRepo.findDayList(month, year);
	}

	@Override
	public Float getExpenseWithDay(int day, int month, int year, Long id) {
		return this.expenseRepo.findExpenseWithDay(day, month, year, id);
	}

	@Override
	public Float getExpenseWithCategory(String category, Long id, int month, int year) {
		return this.expenseRepo.findExpenseWithCategory(category, id, month, year);
	}

	@Override
	public int getNextMonth(String date) {
		return this.expenseRepo.findNextMonth(date);
	}

	@Override
	public int getYearOfNextMonth(String date) {
		return this.expenseRepo.findYearOfNextMonth(date);
	}

	@Override
	public List<Expense> getExpenseByCategory(String category, Long id, int month, int year) {
		return this.expenseRepo.findExpenseByCategory(category, id, month, year);
	}

	@Override
	public List<Expense> getExpenseByDay(int day, int month, int year, Long id) {
		return this.expenseRepo.findExpenseByDay(day, month, year, id);
	}

	@Override
	public List<Expense> getExpenseByMonth(int month, int year, Long id) {
		return this.expenseRepo.findExpenseByMonth(month, year, id);
	}

	@Override
	public Float getExpenseWithCategoryInDay(String category, Long id, int day, int month, int year) {
		return this.expenseRepo.findExpenseWithCategoryInDay(category, id, day, month, year);
	}

}
