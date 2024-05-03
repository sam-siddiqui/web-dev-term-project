package com.MusicStore.Controller;

import java.util.ArrayList;
import java.util.List;

import com.MusicStore.Entity.Student;
import com.MusicStore.Entity.User;
import com.MusicStore.Service.StudentService;
import com.MusicStore.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.MusicStore.Entity.Course;
import com.MusicStore.Service.CourseService;

@Controller
public class CourseController {
	@Autowired
	private CourseService service;
	@Autowired
	private StudentService studentService;

	@GetMapping("/add_course")
	public String addCourse(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		session.setAttribute("error", null);
		return "addCourse";
	}

	@GetMapping("/show_course")
	public String getAllCourse(HttpSession session, Model m) {
		if(session.getAttribute("user") == null) return "redirect:/login";
		List<Course> list = null;

		User currentUser = (User) session.getAttribute("user");
		String currentUserRole = currentUser.getUsercontrol();
		String currentUserID = String.valueOf(currentUser.getId());

		if(currentUserRole.equals("1")){
			list = service.getAllCourse();
			m.addAttribute("currentUserRole", "Admin");
		}

		if(currentUserRole.equals("2")) {
			list = service.getCoursesByTeacherID(currentUserID);
			m.addAttribute("currentUserRole", "Teacher");
		}

		if(currentUserRole.equals("3")) {
			Student fromCurrentUser = studentService.getStudentbyId(Integer.parseInt(currentUserID));
			if(fromCurrentUser == null)
				list = new ArrayList<>();
			else {
				String studentScheduleID = String.valueOf(fromCurrentUser.getSchedule_id());
				list = service.getCoursesByStudentScheduleID(studentScheduleID);
			}

			m.addAttribute("currentUserRole", "Student");
		}

		m.addAttribute("course", list);
		return "showCourse";
	}

	@PostMapping("/savecourse")
	public String addCourse(HttpSession session, @ModelAttribute @NonNull Course c) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewCourse(c);
		boolean isValidCourseDetails = errorMessage.isEmpty();

		if(!isValidCourseDetails) {
			session.setAttribute("error", errorMessage);
			return "redirect:/add_course";
		};

		service.save(c);
		return "redirect:/show_course";
	}

	@RequestMapping("deleteCourse/{id}")
	public String deleteCourse(HttpSession session, @PathVariable("id") int id) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		service.deleteById(id);
		return "redirect:/show_course";
	}

	@GetMapping("editCourse/{id}")
	public String editCourse(HttpSession session, @PathVariable("id") int id, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";
		Course c = null;
		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		if(session.getAttribute("oldDetailsC") != null)
			c = (Course) session.getAttribute("oldDetailsC");
		else
			c = service.getCoursebyId(id);

		model.addAttribute("course", c);
		session.setAttribute("error", null);
		return "editCourse";

	}

	@PostMapping("editCourse/{id}")
	public String updateCourse(HttpSession session, @PathVariable("id") int id, Model model, @ModelAttribute @NonNull Course c) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewCourse(c);
		boolean isValidCourseDetails = errorMessage.isEmpty();

		if(!isValidCourseDetails) {
			session.setAttribute("error", errorMessage);
			session.setAttribute("oldDetailsC", c);
			return "redirect:/editCourse/"+id;
		};

		service.save(c);
		session.setAttribute("oldDetailsC", null);
		return "redirect:/show_course";
	}

	@GetMapping("getCourseDetails/{id}")
	@ResponseBody
	public Course getCourseDetails(@PathVariable int id) {
		return service.getCoursebyId(id);
	}

}
