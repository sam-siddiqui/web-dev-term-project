package com.MusicStore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MusicStore.Entity.User;
import com.MusicStore.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String login(HttpSession session,@RequestParam String email, @RequestParam String password, Model model) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {
			// User authenticated, redirect to dashboard or any other page
			String username = (String) session.getAttribute("username");
	        model.addAttribute("username", username);
			return "redirect:/dash_board";
		} else {
			// Invalid credentials, add error message to model
			model.addAttribute("error", "Invalid email or password");
			return "login";
		}
	}
}
