package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Expense;
import com.nguyenhung.bta.entity.User;

public interface ExpenseService {
	List<Expense> getAllExpenses();
	Expense getExpenseById(Long expense_id);
	Expense saveExpense(Expense expense);
	void removeExpense(Long id);
	List<Expense> getExpensesByUser(User user);
	Float getExpenseTotal(Long id, int month, int year);
	
	Float getExpenseWithDay(int day, int month, int year, Long id);
	List<Expense> getExpenseByDay(int day, int month, int year, Long id);
	List<Expense> getExpenseByMonth(int month, int year, Long id);
	Float getExpenseWithCategory(String category, Long id, int month, int year);
	Float getExpenseWithCategoryInDay(String category, Long id, int day, int month, int year);
	List<Expense> getExpenseByCategory(String category, Long id, int month, int year);
	
	int getLastMonth(String date);
	int getYearOfLastMonth(String date);
	int getNextMonth(String date);
	int getYearOfNextMonth(String date);
	List<Integer> getDayList(int month, int year);
}
