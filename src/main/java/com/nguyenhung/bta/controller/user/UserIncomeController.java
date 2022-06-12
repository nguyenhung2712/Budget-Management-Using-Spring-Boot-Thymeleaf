package com.nguyenhung.bta.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.entity.Income;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.service.IncomeService;
import com.nguyenhung.bta.service.SourceService;
import com.nguyenhung.bta.service.UserService;

@Controller
@RequestMapping("/user")
public class UserIncomeController {
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private SourceService sourceService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/incomes")
	public String incomesListPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		model.addAttribute("incomesList", this.incomeService.getIncomesByUser(currentUser));
		return "user/income/incomes";
	}
	
	@GetMapping("/create-income")
	public String createIncomePage(Model model) {
		model.addAttribute("income", new Income());
		model.addAttribute("sourcesList", this.sourceService.getAllSources());
		return "user/income/create";
	}
	@GetMapping("/update-income/{income_id}")
	public String updateIncomePage(@PathVariable("income_id") Long income_id, Model model) {
		Income income = this.incomeService.getIncomeById(income_id);
		model.addAttribute("income", income);
		model.addAttribute("sourcesList", this.sourceService.getAllSources());
		return "user/income/update";
	}
	
	@PostMapping("/save-income")
	public String saveIncome(@ModelAttribute("income") Income income,
			RedirectAttributes redirectAttrs) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		income.setUser(currentUser);
		try {
			this.incomeService.saveIncome(income);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
		}
		return "redirect:/user/incomes";
	}
	
	@GetMapping("/delete-income/{income_id}")
	public String deleteIncome(@PathVariable("income_id") Long income_id,
			RedirectAttributes redirectAttrs) {
		try {
			this.incomeService.removeIncome(income_id);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/user/incomes";
	}
}
