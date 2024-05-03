package com.MusicStore.Controller;

import java.util.ArrayList;
import java.util.List;

import com.MusicStore.Entity.*;
import com.MusicStore.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.MusicStore.Service.CourseService;
import com.MusicStore.Service.ScheduleService;
import com.MusicStore.Service.ShowScheduleService;
import com.MusicStore.Service.TeacherService;

@Controller
public class ScheduleController {
	@Autowired
	private TeacherService tservice;
	@Autowired
	private CourseService cservice;
	@Autowired
	private ScheduleService schservice;
	@Autowired
	private ShowScheduleService show_schservice;

	@GetMapping("/add_schedule")
	public String setSchedule(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		List<Schedule> sclist = schservice.getAllSchedule();

		ArrayList<String> daysOfWeek = Utils.getDaysOfWeek();
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();

		model.addAttribute("schedule", sclist);
		model.addAttribute("teacher", tlist);

		model.addAttribute("course", clist);
		model.addAttribute("daysOfWeek", daysOfWeek);

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		if(currentUser.getUsercontrol().equals("1"))
			model.addAttribute("currentTeacherID", "-1");
		else if(currentUser.getUsercontrol().equals("2"))
			model.addAttribute("currentTeacherID", currentUser.getId());


		session.setAttribute("error", null);
		return "addSchedule";
	}

	@GetMapping("/teacher_schedule")
	public String teacherSchedule(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		List<Show_Schedule> sclist = null;

		if(currentUser.getUsercontrol().equals("1"))
				sclist = show_schservice.getAllShowSchedule();
		else if(currentUser.getUsercontrol().equals("2"))
			sclist = show_schservice.getScheduleByTeacherID(String.valueOf(currentUser.getId()));


		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();
		model.addAttribute("teacher", tlist);
		model.addAttribute("course", clist);
		model.addAttribute("schedule", sclist);
		return "teacherSchedule";
	}

	@GetMapping("/editSchedule/{id}")
	public String editSchedule(HttpSession session, @PathVariable("id") int id, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		if(session.getAttribute("error") != null)
			model.addAttribute("error", session.getAttribute("error"));

		Show_Schedule s = null;
		if(session.getAttribute("error") != null)
			s = (Show_Schedule) session.getAttribute("oldValuesSch");
		else
			s = show_schservice.getShowSchedulebyId(id);

		session.setAttribute("error", null);
		ArrayList<String> daysOfWeek = Utils.getDaysOfWeek();
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();
		model.addAttribute("teacher", tlist);
		model.addAttribute("course", clist);
		model.addAttribute("schedule", s);
		model.addAttribute("daysOfWeek", daysOfWeek);

		return "editSchedule";
	}

	@PostMapping("/editSchedule/{id}")
	public String updateSchedule(HttpSession session, @PathVariable("id") int id, Model model, @ModelAttribute @NonNull Show_Schedule s) {
		if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewSchedule(s);
		boolean isValidScheduleDetails = errorMessage.isEmpty();

		if(!isValidScheduleDetails) {
			session.setAttribute("error", errorMessage);
			session.setAttribute("oldValuesSch", s);
			return "redirect:/editSchedule/"+id;
		}

		show_schservice.save(s);
		return "redirect:/teacher_schedule";
	}

	@PostMapping("/saveschedule")
	public String addSchedule(HttpSession session, @ModelAttribute @NonNull Schedule s) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		String errorMessage = Utils.validateNewSchedule(s);
		boolean isValidScheduleDetails = errorMessage.isEmpty();

		if(!isValidScheduleDetails) {
			session.setAttribute("error", errorMessage);
			return "redirect:/add_schedule";
		}

		schservice.save(s);
		return "redirect:/teacher_schedule";
	}

	@RequestMapping("deleteSchedule/{id}")
	public String deleteTeacher(HttpSession session, @PathVariable("id") int id) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		show_schservice.deleteById(id);
		return "redirect:/show_schedule";

	}

	@GetMapping("getScheduleDetails/{id}")
	@ResponseBody
	public Show_Schedule getScheduleDetails(@PathVariable int id) {
		return show_schservice.getShowSchedulebyId(id);
	}

}
