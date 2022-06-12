package com.nguyenhung.bta.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.entity.Category;
import com.nguyenhung.bta.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String categoriesListPage(Model model) {
		model.addAttribute("categoriesList", this.categoryService.getAllCategories());
		model.addAttribute("category", new Category());
		return "admin/category/categories";
	}
	
	@GetMapping("/update-category/{csid}")
	public String updateCategoryPage(@PathVariable("csid") Long csid,
			Model model) {
		model.addAttribute("category", this.categoryService.getCategoryById(csid));
		return "admin/category/update";
	}
	
	@PostMapping("/save-category")
	public String saveCategory(@Valid @ModelAttribute("category") Category category,
			BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
			@Param("img") String img,
			Model model,
			RedirectAttributes redirectAttrs) throws IOException {
		if (bindingResult.hasErrors()) {
			if (img == null) {
				model.addAttribute("categoriesList", this.categoryService.getAllCategories());
				model.addAttribute("category", new Category());
				return "admin/category/categories";
			} else {
				model.addAttribute("category", this.categoryService.getCategoryById(category.getCsid()));
				return "admin/category/update";
			}
		} else {
			Path path = Paths.get("src/main/resources/static/admin/img/cs_icon/");
			if (file.isEmpty()) {
				category.setIcon(img);		
			} else {
				category.setIcon(file.getOriginalFilename());
				try {						  
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			try {
				this.categoryService.saveCategory(category);
				redirectAttrs.addFlashAttribute("alertType", "success");
				redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
			} catch (Exception e) {
				redirectAttrs.addFlashAttribute("alertType", "danger");
				redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
			}
			return "redirect:/admin/categories";
		}
	}
	
	@GetMapping("/delete-category/{csid}")
	public String deleteCategory(@PathVariable("csid") Long csid,
			RedirectAttributes redirectAttrs) {
		try {
			this.categoryService.removeCategory(csid);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/admin/categories";
	}
}
