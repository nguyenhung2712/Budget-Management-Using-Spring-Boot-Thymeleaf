package com.nguyenhung.bta.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.service.LogService;

@Controller
@RequestMapping("/admin")
public class LogController {
	@Autowired
	private LogService logService;
	
	@GetMapping("/logs")
	public String categoriesListPage(Model model) {
		model.addAttribute("logsList", this.logService.getAllLogs());
		return "admin/log/main";
	}
	@GetMapping("/delete-log/{id}")
	public String deleteCategory(@PathVariable("id") Long id,
			RedirectAttributes redirectAttrs) {
		try {
			this.logService.removeLog(id);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/admin/logs";
	}
}
