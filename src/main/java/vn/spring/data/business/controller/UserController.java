package vn.spring.data.business.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
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
		return "redirect:/confirm/user/" + us;
	}

	@GetMapping("/confirm/user/{id}")
	public String confirmUserForm(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "confirm";
	}

	@PostMapping("/confirm/{id}")
	public String confirmUsers(@PathVariable Long id, @ModelAttribute User user) {
		User users = userService.getUserById(id);
		if (user.getPassword().equals(users.getPassword())) {
			return "redirect:/home";
		}
		return "redirect:/confirm/user/" + id;
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user) {
		List<User> userList = userService.getAllUser();
		boolean flag = userList.stream().anyMatch(it -> Objects.equals(it.getUsername(), user.getUsername())
				&& Objects.equals(it.getPassword(), user.getPassword()));
		if (flag) {
			return "redirect:/students";
		}
		return "login";
	}
}
