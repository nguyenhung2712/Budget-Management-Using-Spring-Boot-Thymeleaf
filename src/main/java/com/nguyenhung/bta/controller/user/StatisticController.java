package com.nguyenhung.bta.controller.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nguyenhung.bta.entity.Category;
import com.nguyenhung.bta.entity.Expense;
import com.nguyenhung.bta.entity.Income;
import com.nguyenhung.bta.entity.Source;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.service.CategoryService;
import com.nguyenhung.bta.service.ExpenseService;
import com.nguyenhung.bta.service.IncomeService;
import com.nguyenhung.bta.service.SourceService;
import com.nguyenhung.bta.service.UserService;

@Controller
@RequestMapping("/user")
public class StatisticController {
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private SourceService sourceService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;	

	public Map<String, Object> getTotalData(Long id, int month, int year) {
		Map<String, Object> data = new HashMap<>();
		
		Float income = this.incomeService.getIncomeTotal(id, month, year);
		Float expense = this.expenseService.getExpenseTotal(id, month, year);
		data.put("incomeTotal", income == null ? 0 : income);
		data.put("expenseTotal", expense == null ? 0 : expense);
		
		return data;
	}
		
	public Map<String, Object> getAreaData(Long id, int month, int year) {
		Map<String, Object> data = new HashMap<>();
		
		List<Integer> dayList = this.expenseService.getDayList(month, year);
		List<String> areaChartLabels = new ArrayList<>();
		List<Float> areaChartIncomeData = new ArrayList<>();
		List<Float> areaChartExpenseData = new ArrayList<>();
				
		dayList.forEach(day -> {
			Float incomeTotal = this.incomeService.getIncomeWithDay(day, month, year, id);
			Float expenseTotal = this.expenseService.getExpenseWithDay(day, month, year, id);
			areaChartLabels.add(String.valueOf(day));
			areaChartIncomeData.add(incomeTotal != null ? incomeTotal : 0);
			areaChartExpenseData.add(expenseTotal != null ? -expenseTotal : 0);
		});
		Collections.reverse(areaChartLabels);
		Collections.reverse(areaChartExpenseData);
		Collections.reverse(areaChartIncomeData);
		data.put("areaLabel", areaChartLabels);
		data.put("dayExpense", areaChartExpenseData);
		data.put("dayIncome", areaChartIncomeData);
		return data;
	}
	
	public Map<String, Object> getExpenseDoughnutData(Long id, int month, int year) {
		Map<String, Object> data = new HashMap<>();
		
		List<String> doughnutExpenseLabels = new ArrayList<>();
		List<Float> doughnutExpenseData = new ArrayList<>();
		
		this.categoryService.getAllCategories().forEach(category -> {
			doughnutExpenseLabels.add(category.getName());
		});
		Iterator<String> expenseLabels = doughnutExpenseLabels.iterator();
		while (expenseLabels.hasNext()) {
			String label = expenseLabels.next();
			Float total = this.expenseService.getExpenseWithCategory(label, id, month, year);
			if (total != null) {
				doughnutExpenseData.add(total);
			} else {
				expenseLabels.remove();
			}
		}
		data.put("doughnutExpenseLabel", doughnutExpenseLabels);
		data.put("doughnutExpenseData", doughnutExpenseData);
		
		return data;
	}
	
	public Map<String, Object> getIncomeDoughnutData(Long id, int month, int year) {
		Map<String, Object> data = new HashMap<>();
		List<String> doughnutIncomeLabels = new ArrayList<>();
		List<Float> doughnutIncomeData = new ArrayList<>();
		
		this.sourceService.getAllSources().forEach(source -> {
			doughnutIncomeLabels.add(source.getName());
		});
		Iterator<String> incomeLabels = doughnutIncomeLabels.iterator();
		while (incomeLabels.hasNext()) {
			String label = incomeLabels.next();
			Float total = this.incomeService.getIncomeWithSource(label, id, month, year);
			if (total != null) {
				doughnutIncomeData.add(total);
			} else {
				incomeLabels.remove();
			}
		}
		
		data.put("doughnutIncomeLabel", doughnutIncomeLabels);
		data.put("doughnutIncomeData", doughnutIncomeData);
		return data;
	}
	
	@GetMapping(value = {"", "/chart"})
	public String defaultChartPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = new Date();  
        int month = Integer.parseInt(formatter.format(date).substring(5, 7));
        int year = Integer.parseInt(formatter.format(date).substring(0, 4));
        
        Map<String, Object> areaData = this.getAreaData(id, month, year);
        Map<String, Object> totalData = this.getTotalData(id, month, year);
        Map<String, Object> doughnutExpenseData = this.getExpenseDoughnutData(id, month, year);
        Map<String, Object> doughnutIncomeData = this.getIncomeDoughnutData(id, month, year);
        List<Float> dayExpense = (List<Float>) areaData.get("dayExpense");
		List<Float> dayIncome = (List<Float>) areaData.get("dayIncome");
		List<Float> doughnutExpense = (List<Float>) doughnutExpenseData.get("doughnutExpenseData");
		List<Float> doughnutIncome = (List<Float>) doughnutIncomeData.get("doughnutIncomeData");
		if (doughnutIncome.stream().anyMatch(income -> income != 0)) {
			model.addAttribute("haveDIncome", true);
		} else {
			model.addAttribute("haveDIncome", false);
		}
		if (doughnutExpense.stream().anyMatch(expense -> expense != 0)) {
			model.addAttribute("haveDExpense", true);
		} else {
			model.addAttribute("haveDExpense", false);
		}
		if (dayExpense.stream().anyMatch(expense -> expense != 0) 
				|| dayIncome.stream().anyMatch(income -> income != 0)) {
			model.addAttribute("haveInfo", true);
		} else if (dayExpense.stream().allMatch(expense -> expense == 0) 
				|| dayIncome.stream().allMatch(income -> income == 0)) {
			model.addAttribute("haveInfo", false);
		}
		model.addAttribute("incomeTotal", totalData.get("incomeTotal"));
		model.addAttribute("expenseTotal", totalData.get("expenseTotal"));
		model.addAttribute("doughnutExpenseLabel", doughnutExpenseData.get("doughnutExpenseLabel"));
		model.addAttribute("doughnutIncomeLabel", doughnutIncomeData.get("doughnutIncomeLabel"));
		model.addAttribute("doughnutExpenseData", doughnutExpense);
		model.addAttribute("doughnutIncomeData", doughnutIncome);
		model.addAttribute("areaLabel", areaData.get("areaLabel"));
		model.addAttribute("dayExpense", dayExpense);
		model.addAttribute("dayIncome", dayIncome);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
		return "user/statistic/chart";
	}
	
	@GetMapping("/chart/{month}/{year}/{type}")
	public String preOrNextMonthChartPage(@PathVariable("month") int month,
			@PathVariable("year") int year,
			@PathVariable("type") String type,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		String date = year + "-" + month + "-" + 1;
		int monthChanged = 0, yearChanged = 0;
		if (type.equals("pre")) {
			monthChanged = this.expenseService.getLastMonth(date);
			yearChanged = this.expenseService.getYearOfLastMonth(date);
		} else {
			monthChanged = this.expenseService.getNextMonth(date);
			yearChanged = this.expenseService.getYearOfNextMonth(date);
		}
		Map<String, Object> areaData = this.getAreaData(id, monthChanged, yearChanged);
        Map<String, Object> totalData = this.getTotalData(id, monthChanged, yearChanged);
        Map<String, Object> doughnutExpenseData = this.getExpenseDoughnutData(id, monthChanged, yearChanged);
        Map<String, Object> doughnutIncomeData = this.getIncomeDoughnutData(id, monthChanged, yearChanged);
        List<Float> dayExpense = (List<Float>) areaData.get("dayExpense");
		List<Float> dayIncome = (List<Float>) areaData.get("dayIncome");
		List<Float> doughnutExpense = (List<Float>) doughnutExpenseData.get("doughnutExpenseData");
		List<Float> doughnutIncome = (List<Float>) doughnutIncomeData.get("doughnutIncomeData");
		if (doughnutIncome.stream().anyMatch(income -> income != 0)) {
			model.addAttribute("haveDIncome", true);
		} else {
			model.addAttribute("haveDIncome", false);
		}
		if (doughnutExpense.stream().anyMatch(expense -> expense != 0)) {
			model.addAttribute("haveDExpense", true);
		} else {
			model.addAttribute("haveDExpense", false);
		}
		if (dayExpense.stream().anyMatch(expense -> expense != 0) 
				|| dayIncome.stream().anyMatch(income -> income != 0)) {
			model.addAttribute("haveInfo", true);
		} else if (dayExpense.stream().allMatch(expense -> expense == 0) 
				|| dayIncome.stream().allMatch(income -> income == 0)) {
			model.addAttribute("haveInfo", false);
		}
		
		model.addAttribute("incomeTotal", totalData.get("incomeTotal"));
		model.addAttribute("expenseTotal", totalData.get("expenseTotal"));
		model.addAttribute("doughnutExpenseLabel", doughnutExpenseData.get("doughnutExpenseLabel"));
		model.addAttribute("doughnutIncomeLabel", doughnutIncomeData.get("doughnutIncomeLabel"));
		model.addAttribute("doughnutExpenseData", doughnutExpenseData.get("doughnutExpenseData"));
		model.addAttribute("doughnutIncomeData", doughnutIncomeData.get("doughnutIncomeData"));
		model.addAttribute("areaLabel", areaData.get("areaLabel"));
		model.addAttribute("dayExpense", dayExpense);
		model.addAttribute("dayIncome", dayIncome);
		model.addAttribute("month", monthChanged);
		model.addAttribute("year", yearChanged);
		
		return "user/statistic/chart";
	}
	
	@GetMapping("/area-detail/{month}/{year}")
	public String areaDetailPage(@PathVariable("month") int month,
			@PathVariable("year") int year,
			Model model) {
		List<Map<String, Object>> dayData = new ArrayList<>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		Map<String, Object> areaData = this.getAreaData(id, month, year);
		List<Integer> dayList = this.expenseService.getDayList(month, year);
		Collections.reverse(dayList);
		dayList.forEach(day -> {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateString = day + "-" + month + "-" + year;
			String week = "";
			Map<String, Object> temp = new HashMap<>();
			Float expenseValue = this.expenseService.getExpenseWithDay(day, month, year, id);
			Float incomeValue = this.incomeService.getIncomeWithDay(day, month, year, id);
			
			try {
				week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(dateString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			temp.put("day", day);
			temp.put("date", week + ", " + dateString);
			temp.put("income", incomeValue == null ? 0 : incomeValue);
			temp.put("expense", expenseValue == null ? 0 : expenseValue);
			
			dayData.add(temp);
		});
		
		dayData.removeIf(data -> (data.get("expense").equals((float)0) && data.get("income").equals((float)0)));
		List<Float> dayExpense = (List<Float>) areaData.get("dayExpense");
		List<Float> dayIncome = (List<Float>) areaData.get("dayIncome");
		if (dayExpense.stream().anyMatch(expense -> expense != 0) 
				|| dayIncome.stream().anyMatch(income -> income != 0)) {
			model.addAttribute("haveInfo", true);
		} else if (dayExpense.stream().allMatch(expense -> expense == 0) 
				|| dayIncome.stream().allMatch(income -> income == 0)) {
			model.addAttribute("haveInfo", false);
		}
		model.addAttribute("areaLabel", areaData.get("areaLabel"));
		model.addAttribute("dayExpense", dayExpense);
		model.addAttribute("dayIncome", dayIncome);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("dayDataList", dayData);
		return "user/statistic/area_details";
	}
	
	@GetMapping("/day-detail/{day}/{month}/{year}")
	public String mainDayDetailListPage(@PathVariable("day") int day,
			@PathVariable("month") int month,
			@PathVariable("year") int year,
			Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		String week = "";
		try {
			week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(day + "-" + month + "-" + year));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Float expenseInDay = this.expenseService.getExpenseWithDay(day, month, year, id);
		Float incomeInDay = this.incomeService.getIncomeWithDay(day, month, year, id);
		List<Expense> expensesList = this.expenseService.getExpenseByDay(day, month, year, id);
		List<Income> incomesList = this.incomeService.getIncomeByDay(day, month, year, id);
		model.addAttribute("incomesList", incomesList == null ? new ArrayList<>() : incomesList);
		model.addAttribute("expensesList", expensesList == null ? new ArrayList<>() : expensesList);
		model.addAttribute("expenseInDay", expenseInDay == null ? 0 : expenseInDay);
		model.addAttribute("incomeInDay", incomeInDay == null ? 0 : incomeInDay);
		
		model.addAttribute("month", month > 10 ? month : "0" + month);
		model.addAttribute("year", year);
		model.addAttribute("day", day > 10 ? day : "0" + day);
		model.addAttribute("week", week);
		
		return "user/statistic/day_details";
	}
	
	@GetMapping("/day-detail/{day}/{month}/{year}/{type}")
	public String dayDetailListPage(@PathVariable("day") int day,
			@PathVariable("month") int month,
			@PathVariable("year") int year,
			@PathVariable("type") Optional<String> type,
			Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		String week = "";
		try {
			week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(day + "-" + month + "-" + year));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Float expenseInDay = this.expenseService.getExpenseWithDay(day, month, year, id);
		Float incomeInDay = this.incomeService.getIncomeWithDay(day, month, year, id);
		List<Expense> expensesList = this.expenseService.getExpenseByDay(day, month, year, id);
		List<Income> incomesList = this.incomeService.getIncomeByDay(day, month, year, id);
		if (!type.isPresent()) {
			model.addAttribute("incomesList", incomesList == null ? new ArrayList<>() : incomesList);
			model.addAttribute("expensesList", expensesList == null ? new ArrayList<>() : expensesList);
			model.addAttribute("expenseInDay", expenseInDay == null ? 0 : expenseInDay);
			model.addAttribute("incomeInDay", incomeInDay == null ? 0 : incomeInDay);
		} else if (type.get().equals("expense")) {
			model.addAttribute("incomesList", new ArrayList<>());
			model.addAttribute("expensesList", expensesList == null ? new ArrayList<>() : expensesList);
			model.addAttribute("expenseInDay", expenseInDay == null ? 0 : expenseInDay);
			model.addAttribute("incomeInDay", 0);
		} else if (type.get().equals("income")) {
			model.addAttribute("incomesList", incomesList == null ? new ArrayList<>() : incomesList);
			model.addAttribute("expensesList", new ArrayList<>());
			model.addAttribute("expenseInDay", 0);
			model.addAttribute("incomeInDay", incomeInDay == null ? 0 : incomeInDay);
		}
		
		model.addAttribute("month", month > 10 ? month : "0" + month);
		model.addAttribute("year", year);
		model.addAttribute("day", day > 10 ? day : "0" + day);
		model.addAttribute("week", week);
		
		return "user/statistic/day_details";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/doughnut-detail/{month}/{year}/{type}") 
	public String doughnutDetailPage(@PathVariable("month") int month,
			@PathVariable("year") int year,
			@PathVariable("type") String type,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		
		//Doughnut
		List<Map<String, Object>> expenseOrIncomeList = new ArrayList<>();
		Map<String, Object> doughnutData = new HashMap<>();
		List<String> doughnutLabel = new ArrayList<>();
		if (type.equals("expense")) {
			doughnutData = this.getExpenseDoughnutData(id, month, year);
			doughnutLabel = (List<String>) doughnutData.get("doughnutExpenseLabel");
			doughnutLabel.forEach(label -> {
				Map<String, Object> temp = new HashMap<>();
				temp.put("category", this.categoryService.getByName(label));
				temp.put("expense", this.expenseService.getExpenseWithCategory(label, id, month, year));
				expenseOrIncomeList.add(temp);
			});
			model.addAttribute("doughnutExpenseLabel", doughnutLabel);
			model.addAttribute("doughnutExpenseData", doughnutData.get("doughnutExpenseData"));
			model.addAttribute("expenseWithCategoryList", expenseOrIncomeList);
		} else {
			doughnutData = this.getIncomeDoughnutData(id, month, year);
			doughnutLabel = (List<String>) doughnutData.get("doughnutIncomeLabel");
			doughnutLabel.forEach(label -> {
				Map<String, Object> temp = new HashMap<>();
				temp.put("source", this.sourceService.getByName(label));
				temp.put("income", this.incomeService.getIncomeWithSource(label, id, month, year));
				expenseOrIncomeList.add(temp);
			});
			model.addAttribute("doughnutIncomeLabel", doughnutLabel);
			model.addAttribute("doughnutIncomeData", doughnutData.get("doughnutIncomeData"));
			model.addAttribute("incomeWithSourceList", expenseOrIncomeList);
		}
		
		//Area
		List<Map<String, Object>> dayData = new ArrayList<>();
		Map<String, Object> areaData = this.getAreaData(id, month, year);
		List<Float> moneyInDay = new ArrayList<>();
		List<Integer> dayList = this.expenseService.getDayList(month, year);
		Collections.reverse(dayList);
		
		if (type.equals("expense")) {
			moneyInDay = (List<Float>) areaData.get("dayExpense");
		} else {
			moneyInDay = (List<Float>) areaData.get("dayIncome");
		}
		
		dayList.forEach(day -> {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String dateString = day + "-" + month + "-" + year;
			String week = "";
			Map<String, Object> temp = new HashMap<>();
			try {
				week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(dateString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			temp.put("day", day);
			temp.put("date", week + ", " + dateString);
			if (type.equals("expense")) {
				Float expenseValue = this.expenseService.getExpenseWithDay(day, month, year, id);
				temp.put("expense", expenseValue == null ? 0 : expenseValue);
			} else {
				Float incomeValue = this.incomeService.getIncomeWithDay(day, month, year, id);
				temp.put("income", incomeValue == null ? 0 : incomeValue);
			}
			
			dayData.add(temp);
		});
		if (type.equals("expense")) {
			dayData.removeIf(data -> (data.get("expense").equals((float)0)));
			model.addAttribute("dayExpense", moneyInDay);
			model.addAttribute("areaExpensesList", dayData);
		} else {
			dayData.removeIf(data -> (data.get("income").equals((float)0)));
			model.addAttribute("dayIncome", moneyInDay);
			model.addAttribute("areaIncomesList", dayData);
		}
		
		moneyInDay.replaceAll(expense -> Math.abs(expense));
		if (moneyInDay.stream().anyMatch(money -> money != 0)) {
			model.addAttribute("haveInfo", true);
		} else {
			model.addAttribute("haveInfo", false);
		}
		model.addAttribute("areaLabel", areaData.get("areaLabel"));
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
		return "user/statistic/doughnut_details";
	}
	
	@GetMapping("/doughnut-cs-detail/{month}/{year}/{id}/{type}") 
	public String doughnutCSDetailPage(@PathVariable("month") int month,
			@PathVariable("year") int year,
			@PathVariable("id") Long csid,
			@PathVariable("type") String type,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Long id = currentUser.getUser_id();
		List<Integer> dayList = this.expenseService.getDayList(month, year);
		
		List<Map<String, Object>> dayData = new ArrayList<>();
		List<String> areaChartLabels = new ArrayList<>();
		List<Float> areaChartIncomeData = new ArrayList<>();
		List<Float> areaChartExpenseData = new ArrayList<>();
		
		if (type.equals("expense")) {
			Category category = this.categoryService.getCategoryById(csid);
			dayList.forEach(day -> {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = day + "-" + month + "-" + year;
				String week = "";
				Map<String, Object> temp = new HashMap<>();
				try {
					week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(dateString));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				temp.put("day", day);
				temp.put("date", week + ", " + dateString);
				Float expenseTotal = this.expenseService.getExpenseWithCategoryInDay(category.getName(), id, day, month, year);
				areaChartExpenseData.add(expenseTotal != null ? expenseTotal : 0);
				temp.put("expense", expenseTotal != null ? expenseTotal : 0);
				areaChartLabels.add(String.valueOf(day));
				
				dayData.add(temp);
			});
			dayData.removeIf(data -> (data.get("expense").equals((float)0)));
			model.addAttribute("category", category.getName());
			model.addAttribute("areaExpensesList", dayData);
		} else if (type.equals("income")) {
			Source source = this.sourceService.getSourceById(csid);
			dayList.forEach(day -> {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String dateString = day + "-" + month + "-" + year;
				String week = "";
				Map<String, Object> temp = new HashMap<>();
				try {
					week = (new SimpleDateFormat("EEEE", new Locale("vi", "VN"))).format(dateFormat.parse(dateString));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				temp.put("day", day);
				temp.put("date", week + ", " + dateString);
				
				Float incomeTotal = this.incomeService.getIncomeWithSourceInDay(source.getName(), id, day, month, year);
				areaChartIncomeData.add(incomeTotal != null ? incomeTotal : 0);
				temp.put("income", incomeTotal != null ? incomeTotal : 0);
				areaChartLabels.add(String.valueOf(day));
				dayData.add(temp);
			});
			dayData.removeIf(data -> (data.get("income").equals((float)0)));
			model.addAttribute("source", source.getName());
			model.addAttribute("areaIncomesList", dayData);
		}
		
		Collections.reverse(areaChartLabels);
		Collections.reverse(areaChartExpenseData);
		Collections.reverse(areaChartIncomeData);
		model.addAttribute("areaLabel", areaChartLabels);
		if (type.equals("expense")) {
			model.addAttribute("dayExpense", areaChartExpenseData);
		} else {
			model.addAttribute("dayIncome", areaChartIncomeData);
		}
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
		return "user/statistic/doughnut_cs_details";
	}
	
	@GetMapping("/statistic-list")
	public String detailListPage(Model model) {
		return "user/statistic/list";
	}
	
}
