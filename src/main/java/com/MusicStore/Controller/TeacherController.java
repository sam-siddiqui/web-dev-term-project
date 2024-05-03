package com.MusicStore.Controller;

import java.util.List;

import com.MusicStore.Entity.Student;
import com.MusicStore.Entity.User;
import com.MusicStore.Service.StudentService;
import com.MusicStore.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import com.MusicStore.Entity.Teacher;
import com.MusicStore.Service.TeacherService;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService service;

	@Autowired
	private StudentService studentService;

	@GetMapping({"/saveteacher", "/deleteTeacher/*", "/editTeacher/*", "/searchteacher"})
	public String redirectToLoginPage(HttpSession session) {
		return "redirect:/login";
	}

	@GetMapping("/add_teacher")
	public String addTeacher(HttpSession session, Model m) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		if(session.getAttribute("error") != null)
			m.addAttribute("error", session.getAttribute("error"));

		session.setAttribute("error", null);
		return "addTeacher";
	}

	@GetMapping("/show_teacher")
	public String getAllTeacher(HttpSession session, Model m) {
		if(!Utils.checkIfAdminOrStudentLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		String currentUserRole = currentUser.getUsercontrol().equals("1") ? "Admin" :
								 currentUser.getUsercontrol().equals("3") ? "Student" : "Teacher";

		List<Teacher> list = null;

		if(currentUserRole.equals("Admin")) list = service.getAllTeacher();
		if(currentUserRole.equals("Student")) {
			Student currentStudent = studentService.getStudentbyId(Math.toIntExact(currentUser.getId()));
			String currentStudentScheduleID = String.valueOf(currentStudent.getSchedule_id());
			list = service.getAllTeachersByStudentScheduleID(currentStudentScheduleID);
		}

		m.addAttribute("currentUserRole", currentUserRole);
		m.addAttribute("teacher", list);
		return "showTeacher";
	}

	@PostMapping("/saveteacher")
	public String addTeacher(HttpServletRequest request, @ModelAttribute @NonNull Teacher t) {
		HttpSession session = request.getSession(false);
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		System.out.println(request.getRequestURI());

		String errorMessage = Utils.validateNewTeacher(t);
		boolean isValidTeacherDetails = errorMessage.isEmpty();
		if(!isValidTeacherDetails) {
			session.setAttribute("error", errorMessage);
			return "redirect:/add_teacher";
		};


		service.save(t);
		return "redirect:/show_teacher";
	}

	@RequestMapping("deleteTeacher/{id}")
	public String deleteTeacher(HttpSession session, @PathVariable("id") int id) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		service.deleteById(id);
		return "redirect:/show_teacher";

	}

	@GetMapping("editTeacher/{id}")
	public String editTeacher(HttpSession session, @PathVariable("id") int id, Model model) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		Teacher t = null;
		if(session.getAttribute("error") != null)
			t = (Teacher) session.getAttribute("oldValuesT");
		else
			t = service.getTeacherbyId(id);

		session.setAttribute("error", null);
		model.addAttribute("teacher", t);
		return "editTeacher";

	}

	@PostMapping("editTeacher/{id}")
	public String updateTeacher(HttpSession session, @PathVariable("id") int id, @ModelAttribute @NonNull Teacher t, Model model) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewTeacher(t);
		boolean isValidTeacherDetails = errorMessage.isEmpty();
		if(!isValidTeacherDetails) {
			session.setAttribute("error", errorMessage);
			session.setAttribute("oldValuesT", t);
			return "redirect:/editTeacher/"+id;
		};


		service.save(t);
		return "redirect:/show_teacher";

	}

	@RequestMapping("/searchteacher")
	public String searchTeacher(HttpSession session, Model model, @Param("keyword") String keyword) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		List<Teacher> list = service.listAll(keyword);
		model.addAttribute("teacher", list);
		model.addAttribute("keyword", keyword);
		return "showTeacher";
	}
}
