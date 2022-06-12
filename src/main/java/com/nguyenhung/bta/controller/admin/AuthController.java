package com.nguyenhung.bta.controller.admin;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nguyenhung.bta.service.UserService;
import com.nguyenhung.bta.dto.UserDTO;
import com.nguyenhung.bta.entity.Role;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.service.RoleService;

@Controller
@RequestMapping("")
public class AuthController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute UserDTO userDTO, Model model) {
		model.addAttribute("userDTO", userDTO);
		return "authentication/register";
	}
	@PostMapping("/register")
	public String register(@Valid UserDTO userDTO,
			BindingResult result) {
		String password = userDTO.getPassword();
		String confPassword = userDTO.getConfPassword();
		
		if (userService.existsByUsername(userDTO.getUsername())) {
			result.addError(new FieldError("userDTO", "username", "Tên tài khoản đã tồn tại"));
		}
		if (userService.existsByEmail(userDTO.getEmail())) {
			result.addError(new FieldError("userDTO", "email", "Email đã tồn tại"));
		}
		
		if (password != null && confPassword != null) {
			if (!password.equals(confPassword)) {
				result.addError(new FieldError("userDTO", "confPassword", "Mật khẩu xác nhận không khớp"));
			}
		}
		
		if (result.hasErrors()) {
			return "authentication/register";
		}
		
		Set<Role> roles = new HashSet<>();
		roles.add(this.roleService.getByName("ROLE_USER"));
		User user = new User(Long.valueOf(0), userDTO.getName(), userDTO.getEmail(), "default.png", userDTO.getUsername(), null, passwordEncoder.encode(userDTO.getPassword()), null, null, roles);
		this.userService.saveUser(user);
		return "redirect:/login";
	}
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String adminLoginPage(RedirectAttributes redirectAttrs) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
        	return "authentication/login";
        }
        return "redirect:/admin";
    }
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String adminLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
	@GetMapping("/default")
	public String defaultAfterLogin(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_MODERATOR")) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }
	@GetMapping("/error")
	public String errorPage() {
        return "error";
    }
}
