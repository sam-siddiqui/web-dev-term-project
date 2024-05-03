package com.MusicStore.Controller;

import java.util.ArrayList;
import java.util.List;

import com.MusicStore.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.MusicStore.Entity.Billing;
import com.MusicStore.Entity.Course;
import com.MusicStore.Entity.Schedule;
import com.MusicStore.Entity.Student;
import com.MusicStore.Entity.Teacher;
import com.MusicStore.Entity.User;
import com.MusicStore.Entity.ViewStudent;
import com.MusicStore.Service.BillingService;
import com.MusicStore.Service.CourseService;
import com.MusicStore.Service.ScheduleService;
import com.MusicStore.Service.StudentService;
import com.MusicStore.Service.TeacherService;
import com.MusicStore.Service.UserService;
import com.MusicStore.Service.ViewStudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService service;
	@Autowired
	private ScheduleService schservice;
	@Autowired
	private CourseService cservice;
	@Autowired
	private TeacherService tservice;
	@Autowired
	private UserService uservice;
	@Autowired
	private ViewStudentService viewstud;
	@Autowired
	private BillingService billservice;

	@GetMapping({"/savestudent", "/deletestudent/*", "/editstudent/*", "/searchstudent"})
	public String redirectToLoginPage(HttpSession session) {
		return "redirect:/login";
	}

	@GetMapping("/add_student")
	public String addStudent(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		List<Schedule> sclist = null;
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();

		if(currentUser.getUsercontrol().equals("1"))
			sclist = schservice.getAllSchedule();
		else if(currentUser.getUsercontrol().equals("2"))
			sclist = schservice.getScheduleByTeacherID(String.valueOf(currentUser.getId()));

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		model.addAttribute("course", clist);
		model.addAttribute("teacher", tlist);
		model.addAttribute("schedule", sclist);
		session.setAttribute("error", null);
		return "addStudent";
	}

	@GetMapping("editStudent/?/")
	public String Change(HttpSession session) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login"; return "../";
	}

	@GetMapping("/show_students")
	public String getAllStudent(HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("user");
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		String currentUserName = currentUser.getFullName();
		String currentUserRole = currentUser.getUsercontrol();
		model.addAttribute("username", currentUserName);
		model.addAttribute("userrole", currentUserRole);

		List<Student> slist = null;

		if(currentUserRole.equals("2"))
			slist = service.getStudentsByTeacherID(String.valueOf(currentUser.getId()));



		else if (currentUserRole.equals("1"))
			slist = service.getAllStudent();

		model.addAttribute("username", currentUserName);
		model.addAttribute("student", slist);
		return "ShowStudent";
	}


	@PostMapping("/savestudent")
	public String addStudent(HttpSession session, @ModelAttribute @NonNull Student s,@ModelAttribute Billing b, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";
		String errorMessage = Utils.validateNewStudent(s);
		boolean isValidStudentDetails = errorMessage.isEmpty();

		if(!isValidStudentDetails) {
			session.setAttribute("error", errorMessage);
			return "redirect:/add_student";
		};

		service.save(s);
		b.setStudent(s);
		billservice.save(b);
		session.setAttribute("error", null);
		model.addAttribute("message", "Student added successfully!");
		return "redirect:/show_students";
	}

	@RequestMapping("deleteStudent/{id}")
	public String deleteStudent(HttpSession session, @PathVariable("id") int id, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		service.deleteById(id);
		
		return "redirect:/show_students";

	}

	@GetMapping("editStudent/{id}")
	public String editStudent(HttpSession session, @PathVariable("id") int id, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		List<Schedule> sclist = null;
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();

		Student s = null;

		if(session.getAttribute("oldDetailsS") != null)
			s = (Student) session.getAttribute("oldDetailsS");
		else
			s =	service.getStudentbyId(id);

		if(currentUser.getUsercontrol().equals("1"))
			sclist = schservice.getAllSchedule();
		else if(currentUser.getUsercontrol().equals("2"))
			sclist = schservice.getScheduleByTeacherID(String.valueOf(currentUser.getId()));

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		model.addAttribute("course", clist);
		model.addAttribute("teacher", tlist);
		model.addAttribute("schedule", sclist);
		model.addAttribute("student", s);
		session.setAttribute("error", null);

		return "editStudent";
	}

	@PostMapping("/editStudent/{id}")
	public String updateStudent(HttpSession session, @PathVariable("id") int id, @ModelAttribute @NonNull Student s, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewStudent(s);
		boolean isValidStudentDetails = errorMessage.isEmpty();

		if(!isValidStudentDetails) {
			session.setAttribute("oldDetailsS", s);
			session.setAttribute("error", errorMessage);
			return "redirect:/editStudent/" + id;
		};

		service.save(s);
		session.setAttribute("oldDetailsS", null);
		session.setAttribute("error", null);

		model.addAttribute("message", "Student updated successfully!");
		return "redirect:/show_students";
	}

	@RequestMapping("viewStudent/{id}")
	public String ViewStudent(@PathVariable("id") int id, Model model) {
		ViewStudent vs = viewstud.getViewStudentbyId(id);
		List<User> ulist = uservice.getAllUser();
		List<String> usernames = new ArrayList<>();
		for (User user : ulist) {
			usernames.add(user.getFullName()); 
		}
		model.addAttribute("usernames", usernames);
		model.addAttribute("student", vs);
		return "viewStudent";

	}

	@RequestMapping("/searchstudent")
	public String searchStudent(HttpSession session, Model model, @Param("keyword") String keyword) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		List<User> ulist = uservice.getAllUser();
		List<String> usernames = new ArrayList<>();
		for (User user : ulist) {
			usernames.add(user.getFullName()); 
		}
		List<Student> list = service.listAll(keyword);
		model.addAttribute("usernames", usernames);
		model.addAttribute("student", list);
		model.addAttribute("keyword", keyword);
		return "ShowStudent";
	}
}
