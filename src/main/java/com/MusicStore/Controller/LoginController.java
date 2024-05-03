package com.MusicStore.Controller;

import com.MusicStore.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@GetMapping("/logout")
	public String logoutUser(HttpSession session) { session.invalidate(); return "redirect:/"; }

	@PostMapping("/login")
	public String login(HttpSession session, HttpServletRequest request, @RequestParam String email, @RequestParam String password, Model model) {
		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null && !user.getUsercontrol().equals("0")) {
			session.invalidate();
			session = request.getSession(true);
			// User authenticated, redirect to dashboard or any other page
			session.setAttribute("userLoggedIn", true);
			session.setAttribute("user", user);
			return "redirect:/dash_board";
		} else {
			// Invalid credentials, add error message to model
			if(user == null)
				model.addAttribute("error", "Invalid email or password");

			if(user != null && user.getUsercontrol().equals("0"))
				model.addAttribute("error", "Your account has been created but not yet approved. Please wait for an admin to approve your account.");
			return "login";
		}
	}

	@GetMapping("/register")
	public String registerPage(HttpSession session, Model model) {

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));
		if(session.getAttribute("oldDetailsR") != null)
			model.addAttribute("newUser", (User) session.getAttribute("oldDetailsR"));

		session.setAttribute("error", null);
		return "register";
	}

	@PostMapping("/register")
	public String queueNewUser(HttpSession session, Model model, @ModelAttribute @NonNull User u) {

		String errorMessage = Utils.validateNewUser(u);
		boolean isValidUserDetails = errorMessage.isEmpty();

		if(!isValidUserDetails) {
			session.setAttribute("error", errorMessage);
			session.setAttribute("oldDetailsR", u);
			return "redirect:/register";
		};

		userRepository.save(u);

		model.addAttribute("message", "Your account has been created but not yet approved. Please wait for an admin to approve your account.");
		return "login";
	}
}
