package com.MusicStore.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

	@GetMapping("/add_student")
	public String addStudent(Model model) {
		List<Schedule> sclist = schservice.getAllSchedule();
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();
		model.addAttribute("course", clist);
		model.addAttribute("teacher", tlist);
		model.addAttribute("schedule", sclist);
		return "addStudent";
	}

	@GetMapping("editStudent/?/")
	public String Change() {
		return "../";
	}

	@GetMapping("/show_students")
	public String getAllStudent(Model model) {
		List<Student> slist = service.getAllStudent();
		List<User> ulist = uservice.getAllUser();
		List<String> usernames = new ArrayList<>();
		for (User user : ulist) {
			usernames.add(user.getFullName()); 
		}
		model.addAttribute("usernames", usernames);
		model.addAttribute("student", slist);
		return "ShowStudent";
	}

	@PostMapping("/savestudent")
	public String addStudent(@ModelAttribute Student s,@ModelAttribute Billing b) {
		service.save(s);
		int studentId = s.getStudent_id();
		b.setStudent_id(studentId);
		billservice.save(b);
		return "redirect:/show_students";
	}

	@RequestMapping("deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/show_students";

	}

	@RequestMapping("editStudent/{id}")
	public String editStudent(@PathVariable("id") int id, Model model) {
		Student s = service.getStudentbyId(id);
		model.addAttribute("student", s);
		return "editStudent";

	}

	@RequestMapping("viewStudent/{id}")
	public String ViewStudent(@PathVariable("id") int id, Model model) {
		ViewStudent vs = viewstud.getViewStudentbyId(id);
		model.addAttribute("student", vs);
		return "viewStudent";

	}

	@RequestMapping("/searchstudent")
	public String searchStudent(Model model, @Param("keyword") String keyword) {
		List<Student> list = service.listAll(keyword);
		model.addAttribute("student", list);
		model.addAttribute("keyword", keyword);
		return "ShowStudent";
	}
}
