package com.nguyenhung.bta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nguyenhung.bta.entity.Income;
import com.nguyenhung.bta.entity.User;

public interface IncomeRepository extends JpaRepository<Income, Long> {
	@Query(value = "SELECT SUM(amount) FROM incomes WHERE user_id = ?1 AND "
			+ "MONTH(date) = ?2 AND YEAR(date) = ?3", nativeQuery = true)
	public Float findIncomeTotal(Long id, int month, int year);
	@Query(value = "SELECT SUM(amount) FROM incomes "
			+ "WHERE DAY(date) = ?1 AND MONTH(date) = ?2 AND YEAR(date) = ?3 AND user_id = ?4", 
			nativeQuery = true)
	public Float findIncomeWithDay(int day, int month, int year, Long id);
	
	@Query(value = "SELECT income_id, amount, date, description, csid, user_id FROM incomes "
			+ "WHERE DAY(date) = ?1 AND MONTH(date) = ?2 AND YEAR(date) = ?3 AND user_id = ?4", 
			nativeQuery = true)
	public List<Income> findIncomeByDay(int day, int month, int year, Long id);
	
	@Query(value = "SELECT income_id, amount, date, description, csid, user_id FROM incomes "
			+ "WHERE MONTH(date) = ?1 AND YEAR(date) = ?2 AND user_id = ?3", 
			nativeQuery = true)
	public List<Income> findIncomeByMonth(int month, int year, Long id);
	
	@Query(value = "SELECT SUM(amount) FROM incomes i JOIN "
			+ "sources s ON s.id = i.csid "
			+ "WHERE name = ?1 AND user_id = ?2 AND "
			+ "MONTH(date) = ?3 AND YEAR(date) = ?4",  nativeQuery = true)
	public Float findIncomeWithSource(String source, Long id, int month, int year);
	@Query(value = "SELECT SUM(amount) FROM incomes i JOIN "
			+ "sources s ON s.id = i.csid "
			+ "WHERE name = ?1 AND user_id = ?2 AND "
			+ "DAY(date) = ?3 AND MONTH(date) = ?4 AND YEAR(date) = ?5",  nativeQuery = true)
	public Float findIncomeWithSourceInDay(String source, Long id, int day, int month, int year);
	
	@Query(value = "SELECT income_id, amount, date, description, csid, user_id FROM incomes e JOIN "
			+ "sources c ON c.id = e.csid "
			+ "WHERE name = ?1  AND user_id = ?2 AND "
			+ "MONTH(date) = ?3 AND YEAR(date) = ?4",  nativeQuery = true)
	public List<Income> findIncomeBySource(String source, Long id, int month, int year);
	
	public List<Income> findByUser(User user);
}
