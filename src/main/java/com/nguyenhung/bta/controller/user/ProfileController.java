package com.nguyenhung.bta.controller.user;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.dto.AccountDTO;
import com.nguyenhung.bta.dto.ProfileDTO;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.service.UserService;

@Controller
@RequestMapping("/user")
public class ProfileController {
	@Autowired
	private UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/profile")
	public String profilePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		model.addAttribute("user", currentUser);
		return "user/profile/profile";
	}
	
	@GetMapping("/update-profile")
	public String updateProfilePage(@ModelAttribute ProfileDTO profileDTO,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		profileDTO.setEmail(currentUser.getEmail());
		profileDTO.setImage(currentUser.getImage());
		profileDTO.setName(currentUser.getName());
		profileDTO.setPhone(currentUser.getPhone());
		model.addAttribute("profileDTO", profileDTO);
		
		return "user/profile/update";
	}
	
	@PostMapping("/update-profile")
	public String updateProfile(@Valid ProfileDTO profileDTO,
			BindingResult result,
			@RequestParam("file") MultipartFile file,
			@Param("img") String img,
			RedirectAttributes redirectAttrs,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		Path path = Paths.get("src/main/resources/static/admin/img/user/");
		String dtoEmail = profileDTO.getEmail();
		if (file.isEmpty()) {
			currentUser.setImage(img);			
		} else {
			currentUser.setImage(file.getOriginalFilename());
			try {						  
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if (userService.existsByEmail(dtoEmail) && (!dtoEmail.equals(currentUser.getEmail()))) {
			result.addError(new FieldError("profileDTO", "email", "Email đã tồn tại"));
		}
		if (result.hasErrors()) {
			model.addAttribute("profileDTO", profileDTO);
			return "user/profile/update";
		}
		try {
			currentUser.setEmail(dtoEmail);
			currentUser.setName(profileDTO.getName());
			currentUser.setPhone(profileDTO.getPhone());
			this.userService.saveUser(currentUser);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
		}
		return "redirect:/user/profile";
	}
	@GetMapping("/update-account")
	public String updateAccountPage(@ModelAttribute AccountDTO accountDTO, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		accountDTO.setUsername(currentUser.getUsername());
		accountDTO.setPassword(currentUser.getPassword());
		model.addAttribute("accountDTO", accountDTO);
		return "user/profile/update_account";
	}
	@PostMapping("/update-account")
	public String updateAccount(@Valid AccountDTO accountDTO, 
			BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = this.userService.getUserByUsername(authentication.getName());
		String dtoUsername = accountDTO.getUsername();
		if (userService.existsByUsername(dtoUsername) && (!dtoUsername.equals(currentUser.getUsername()))) {
			result.addError(new FieldError("accountDTO", "username", "Tên tài khoản đã tồn tại"));
		}
		
		if (result.hasErrors()) {
			return "user/profile/update_account";
		}
		
		currentUser.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
		currentUser.setUsername(accountDTO.getUsername());
		this.userService.saveUser(currentUser);
		return "redirect:/logout";
	}
}
