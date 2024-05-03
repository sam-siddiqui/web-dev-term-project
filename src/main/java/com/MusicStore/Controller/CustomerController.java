package com.MusicStore.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.MusicStore.Entity.Customer;
import com.MusicStore.Service.CustomerService;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService service;

	@GetMapping("/add_customer")
	public String addCustomer() {
		return "addCustomer";
	}

	@GetMapping("/show_customer")
	public ModelAndView getAllCustomer() {
		List<Customer> list = service.getAllCustomer();
		ModelAndView cus = new ModelAndView();
		cus.setViewName("showCustomer");
		cus.addObject("customer", list);
		return cus;
	}

}
