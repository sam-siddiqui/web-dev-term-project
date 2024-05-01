package com.MusicStore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.MusicStore.Entity.Course;
import com.MusicStore.Entity.Schedule;
import com.MusicStore.Entity.Show_Schedule;
import com.MusicStore.Entity.Teacher;
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
	public String setSchedule(Model model) {
		List<Schedule> sclist = schservice.getAllSchedule();
		model.addAttribute("schedule", sclist);
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();
		model.addAttribute("teacher", tlist);
		model.addAttribute("course", clist);
		return "addSchedule";
	}

	@GetMapping("/teacher_schedule")
	public String teacherSchedule(Model model) {
		List<Show_Schedule> sclist = show_schservice.getAllShowSchedule();
		List<Teacher> tlist = tservice.getAllTeacher();
		List<Course> clist = cservice.getAllCourse();
		model.addAttribute("teacher", tlist);
		model.addAttribute("course", clist);
		model.addAttribute("schedule", sclist);
		return "teacherSchedule";
	}

	@PostMapping("/saveschedule")
	public String addSchedule(@ModelAttribute @NonNull Schedule s) {
		schservice.save(s);
		return "redirect:/teacher_schedule";
	}

	@GetMapping("getScheduleDetails/{id}")
	@ResponseBody
	public Show_Schedule getScheduleDetails(@PathVariable int id) {
		return show_schservice.getShowSchedulebyId(id);
	}
}
