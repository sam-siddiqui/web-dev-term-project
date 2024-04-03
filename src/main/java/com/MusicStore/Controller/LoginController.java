package com.MusicStore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MusicStore.Entity.User;
import com.MusicStore.Repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {
			// User authenticated, redirect to dashboard or any other page
			return "redirect:/show_students";
		} else {
			// Invalid credentials, add error message to model
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}
}
