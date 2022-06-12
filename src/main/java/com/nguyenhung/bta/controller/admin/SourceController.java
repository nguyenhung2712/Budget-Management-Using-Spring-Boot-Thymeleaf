package com.nguyenhung.bta.controller.admin;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.entity.Source;
import com.nguyenhung.bta.service.SourceService;

@Controller
@RequestMapping("/admin")
public class SourceController {
	@Autowired
	private SourceService sourceService;
	
	@GetMapping("/sources")
	public String sourcesListPage(Model model) {
		model.addAttribute("sourcesList", this.sourceService.getAllSources());
		model.addAttribute("source", new Source());
		return "admin/source/sources";
	}
	
	@GetMapping("/update-source/{csid}")
	public String updateSourcePage(@PathVariable("csid") Long csid,
			Model model) {
		model.addAttribute("source", this.sourceService.getSourceById(csid));
		return "admin/source/update";
	}
	
	@PostMapping("/save-source")
	public String saveSource(@ModelAttribute("source") Source source,
			@RequestParam("file") MultipartFile file,
			@Param("img") String img,
			RedirectAttributes redirectAttrs) {
		Path path = Paths.get("src/main/resources/static/admin/img/cs_icon/");
		if (file.isEmpty()) {
			source.setIcon(img);		
		} else {
			source.setIcon(file.getOriginalFilename());
			try {						  
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		try {
			this.sourceService.saveSource(source);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
		}
		return "redirect:/admin/sources";
	}
	
	@GetMapping("/delete-source/{csid}")
	public String deleteSource(@PathVariable("csid") Long csid,
			RedirectAttributes redirectAttrs) {
		try {
			this.sourceService.removeSource(csid);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/admin/sources";
	}
}
