package com.nguyenhung.bta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nguyenhung.bta.entity.Expense;
import com.nguyenhung.bta.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	@Query(value = "SELECT SUM(amount) FROM expenses WHERE user_id = ?1 AND "
			+ "MONTH(date) = ?2 AND YEAR(date) = ?3", nativeQuery = true)
	public Float findExpenseTotal(Long id, int month, int year);
	
	@Query(value = "SELECT SUM(amount) FROM expenses "
			+ "WHERE DAY(date) = ?1 AND MONTH(date) = ?2 AND YEAR(date) = ?3 AND user_id = ?4", 
			nativeQuery = true)
	public Float findExpenseWithDay(int day, int month, int year, Long id);
	@Query(value = "SELECT expense_id, amount, date, description, csid, user_id FROM expenses "
			+ "WHERE DAY(date) = ?1 AND MONTH(date) = ?2 AND YEAR(date) = ?3 AND user_id = ?4", 
			nativeQuery = true)
	public List<Expense> findExpenseByDay(int day, int month, int year, Long id);
	
	@Query(value = "SELECT expense_id, amount, date, description, csid, user_id FROM expenses "
			+ "WHERE MONTH(date) = ?1 AND YEAR(date) = ?2 AND user_id = ?3", 
			nativeQuery = true)
	public List<Expense> findExpenseByMonth(int month, int year, Long id);
	
	@Query(value = "SELECT SUM(amount) FROM expenses e JOIN "
			+ "categories c ON c.id = e.csid "
			+ "WHERE name = ?1  AND user_id = ?2 AND "
			+ "MONTH(date) = ?3 AND YEAR(date) = ?4",  nativeQuery = true)
	public Float findExpenseWithCategory(String category, Long id, int month, int year);
	@Query(value = "SELECT SUM(amount) FROM expenses e JOIN "
			+ "categories c ON c.id = e.csid "
			+ "WHERE name = ?1  AND user_id = ?2 AND "
			+ "DAY(date) = ?3 AND MONTH(date) = ?4 AND YEAR(date) = ?5",  nativeQuery = true)
	public Float findExpenseWithCategoryInDay(String category, Long id, int day, int month, int year);
	
	@Query(value = "SELECT expense_id, amount, date, description, csid, user_id  FROM expenses e JOIN "
			+ "categories c ON c.id = e.csid "
			+ "WHERE name = ?1  AND user_id = ?2 AND "
			+ "MONTH(date) = ?3 AND YEAR(date) = ?4",  nativeQuery = true)
	public List<Expense> findExpenseByCategory(String category, Long id, int month, int year);
	
	public List<Expense> findByUser(User user);
	
	
	// Get necessary date
	@Query(value = "SELECT MONTH(:#{#date} - INTERVAL 1 MONTH)", nativeQuery = true)
	public int findLastMonth(@Param("date") String date);
	@Query(value = "SELECT MONTH(:#{#date} + INTERVAL 1 MONTH)", nativeQuery = true)
	public int findNextMonth(@Param("date") String date);
	@Query(value = "SELECT YEAR(:#{#date} - INTERVAL 1 MONTH)", nativeQuery = true)
	public int findYearOfLastMonth(@Param("date") String date);
	@Query(value = "SELECT YEAR(:#{#date} + INTERVAL 1 MONTH)", nativeQuery = true)
	public int findYearOfNextMonth(@Param("date") String date);
	@Query(value = "select day(a.Date) "
			+ "from ("
			+ "    select last_day(date(concat_ws('-', ?2, ?1, 1))) - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date "
			+ "    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a "
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b "
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c "
			+ ") a "
			+ "where Month(a.Date) = ?1 and Year(a.Date) = ?2", nativeQuery = true)
	public List<Integer> findDayList(int month, int year);
}
