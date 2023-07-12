package vn.spring.data.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import vn.spring.data.business.entity.Student;
import vn.spring.data.business.entity.User;
import vn.spring.data.business.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/register")
	public String registerUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}
	@GetMapping("/login")
	public String loginUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}
	@PostMapping("/register")
	public String saveUsers(@ModelAttribute("user") User user) {
		User users = userService.saveUser(user);
		Long us = userService.getById(users.getId());
		return "redirect:/confirm/" + us;
	}
	@GetMapping("/confirm/{id}")
	public String confirmUserForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "confirm";
	}
	@PostMapping("/home")
	public String confirmUsers(@PathVariable Long id ,@ModelAttribute User user) {
		User users = userService.getUserById(id);
		if (user.getPassword().equalsIgnoreCase(users.getPassword())) {
			return "redirect:/home";
		}
		return "";
	}
}
