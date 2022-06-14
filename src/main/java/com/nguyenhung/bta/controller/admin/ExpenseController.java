package com.nguyenhung.bta.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.entity.Expense;
import com.nguyenhung.bta.service.CategoryService;
import com.nguyenhung.bta.service.ExpenseService;
import com.nguyenhung.bta.service.UserService;

@Controller
@RequestMapping("/admin")
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/expenses")
	public String expensesListPage(Model model) {
		model.addAttribute("expensesList", this.expenseService.getAllExpenses());
		return "admin/expense/expenses";
	}
	
	@GetMapping("/create-expense")
	public String createExpensePage(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("usersList", this.userService.getAllUsers());
		model.addAttribute("categoriesList", this.categoryService.getAllCategories());
		return "admin/expense/create";
	}
	@GetMapping("/update-expense/{expense_id}")
	public String updateExpensePage(@PathVariable("expense_id") Long expense_id, Model model) {
		Expense expense = this.expenseService.getExpenseById(expense_id);
		model.addAttribute("expense", expense);
		model.addAttribute("usersList", this.userService.getAllUsers());
		model.addAttribute("categoriesList", this.categoryService.getAllCategories());
		return "admin/expense/update";
	}
	
	@PostMapping("/save-expense")
	public String saveExpense(@ModelAttribute("expense") Expense expense,
            Model model,
			RedirectAttributes redirectAttrs) throws IOException {
		try {
			this.expenseService.saveExpense(expense);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
		}
		return "redirect:/admin/expenses";
	}
	
	@GetMapping("/delete-expense/{expense_id}")
	public String deleteExpense(@PathVariable("expense_id") Long expense_id,
			RedirectAttributes redirectAttrs) {
		try {
			this.expenseService.removeExpense(expense_id);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/admin/expenses";
	}
}
