package com.nguyenhung.bta.controller.admin;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.service.RoleService;
import com.nguyenhung.bta.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/users")
	public String usersListPage(Model model) {
		model.addAttribute("usersList", this.userService.getAllUsers());
		return "admin/user/users";
	}
	
	@GetMapping("/create-user")
	public String createUserPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("rolesList", this.roleService.getAllRoles());
		return "admin/user/create";
	}
	@GetMapping("/update-user/{user_id}")
	public String updateUserPage(@PathVariable("user_id") Long user_id, Model model) {
		User user = this.userService.getUserById(user_id);
		model.addAttribute("user", user);
		model.addAttribute("rolesList", this.roleService.getAllRoles());
		return "admin/user/update";
	}
	
	@PostMapping("/save-user")
	public String saveUser(@Valid @ModelAttribute("user") User user,
			BindingResult result,
			@RequestParam("file") MultipartFile file,
			@Param("img") String img,
			RedirectAttributes redirectAttrs,
			Model model) {
		String username = user.getUsername();
		String email = user.getEmail();
		if (img == null) {
			if (userService.existsByUsername(username)) {
				result.addError(new FieldError("user", "username", "Tên tài khoản đã tồn tại"));
			}
			if (userService.existsByEmail(email)) {
				result.addError(new FieldError("user", "email", "Email đã tồn tại"));
			}
		} else {
			if (userService.existsByEmail(email) && (!email.equals(userService.getUserById(user.getUser_id()).getEmail()))) {
				result.addError(new FieldError("user", "email", "Email đã tồn tại"));
			}
			if (userService.existsByUsername(username) && (!username.equals(userService.getUserById(user.getUser_id()).getUsername()))) {
				result.addError(new FieldError("user", "username", "Tên tài khoản  đã tồn tại"));
			}
		}
		
		if (result.hasErrors()) {
			model.addAttribute("rolesList", this.roleService.getAllRoles());
			if (img == null) {
				model.addAttribute("user", new User());
				return "admin/user/create";
			} else {
				model.addAttribute("user", user);
				return "admin/user/update";
			}
		} else {
			Path path = Paths.get("src/main/resources/static/admin/img/user/");
			if (file.isEmpty()) {
				user.setImage(img);			
			} else {
				user.setImage(file.getOriginalFilename());
				try {						  
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			try {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				this.userService.saveUser(user);
				redirectAttrs.addFlashAttribute("alertType", "success");
				redirectAttrs.addFlashAttribute("alertText", "Lưu mới thành công");
			} catch (Exception e) {
				redirectAttrs.addFlashAttribute("alertType", "danger");
				redirectAttrs.addFlashAttribute("alertText", "Lưu mới thất bại");
			}
			return "redirect:/admin/users";
		}
	}
	
	@GetMapping("/delete-user/{user_id}")
	public String deleteUser(@PathVariable("user_id") Long user_id,
			RedirectAttributes redirectAttrs) {
		try {
			this.userService.removeUser(user_id);
			redirectAttrs.addFlashAttribute("alertType", "success");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thành công");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("alertType", "danger");
			redirectAttrs.addFlashAttribute("alertText", "Xóa thất bại");
		}
		return "redirect:/admin/users";
	}
}
