package com.MusicStore.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MusicStore.Entity.Billing;
import com.MusicStore.Entity.Paybill;
import com.MusicStore.Entity.Schedule;
import com.MusicStore.Entity.Student;
import com.MusicStore.Service.BillingService;
import com.MusicStore.Service.PaybillService;
import com.MusicStore.Service.ScheduleService;
import com.MusicStore.Service.StudentService;

@Controller
public class BillingController {
	@Autowired
	private BillingService billservice;
	@Autowired
	private StudentService stuservice;
	@Autowired
	private PaybillService pbservice;
	@Autowired
	private ScheduleService schservice;


	@GetMapping("/show_billing")
	public String getAllBilling(Model model) {
		List<Billing> pendingBilling = billservice.getPendingBilling();
		List<Student> slist = stuservice.getAllStudent();
		model.addAttribute("student", slist);
	    model.addAttribute("billing", pendingBilling);
		return "ShowBilling";
	}
	@GetMapping("/show_paidbilling")
	public String getAllPaidBilling(Model model) {
		List<Billing> paidBilling = billservice.getPaidBilling();
		List<Student> slist = stuservice.getAllStudent();
		model.addAttribute("student", slist);
	    model.addAttribute("billing", paidBilling);
		return "ShowPaidBilling";
	}
	

	@GetMapping("/add_billing")
	public String addStudent(Model model) {
		List<Student> sclist = stuservice.getAllStudent();
		model.addAttribute("student", sclist);
		return "addBilling";
	}

	@PostMapping("/savebilling")
	public String addStudent(@ModelAttribute @NonNull Billing b) {
		billservice.save(b);
		return "redirect:/show_billing";
	}

	@RequestMapping("deleteBilling/{id}")
	public String deleteBilling(@PathVariable("id") int id) {
		billservice.deleteById(id);
		return "redirect:/show_billing";

	}

	@RequestMapping("editBilling/{id}")
	public String editBilling(@PathVariable("id") int id, Model model) {
		Billing b = billservice.getBillingbyId(id);
		model.addAttribute("billing", b);
		return "editBilling";

	}
	@RequestMapping("payBilling/{id}")
	public String ViewBilling(@PathVariable("id") int id, Model model) {
		Paybill pb = pbservice.getpayBillingbyId(id);
		List<Student> slist = stuservice.getAllStudent();
		List<Schedule> schlist = schservice.getAllSchedule();
		model.addAttribute("schedule", schlist);
		model.addAttribute("student", slist);
		model.addAttribute("billing", pb);
		return "payBilling";

	}
	 @PostMapping("/billing/updatePaymentStatus")
	    public String updatePaymentStatus(@RequestParam @NonNull Integer billingId, @RequestParam String payment_status) {
		 billservice.updatePaymentStatus(billingId, payment_status);
		 return "redirect:/show_billing";
	    }
	@RequestMapping("/searchbilling")
	public String searchBilling(Model model, @Param("bill_keyword") int bill_keyword) {
		List<Billing> list = billservice.listAll(bill_keyword);
		model.addAttribute("bill", list);
		model.addAttribute("bill_keyword", bill_keyword);
		return "ShowBilling";
	}


}
