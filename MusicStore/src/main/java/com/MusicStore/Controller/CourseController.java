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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.MusicStore.Entity.Course;
import com.MusicStore.Service.CourseService;

@Controller
public class CourseController {
	@Autowired
	private CourseService service;

	@GetMapping("/add_course")
	public String addCourse() {
		return "addCourse";
	}

	@GetMapping("/show_course")
	public ModelAndView getAllCourse() {
		List<Course> list = service.getAllCourse();
		ModelAndView cur = new ModelAndView();
		cur.setViewName("showCourse");
		cur.addObject("course", list);
		return cur;
	}

	@PostMapping("/savecourse")
	public String addCourse(@ModelAttribute @NonNull Course c) {
		service.save(c);
		return "redirect:/show_course";
	}

	@RequestMapping("deleteCourse/{id}")
	public String deleteCourse(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/show_course";
	}

	@RequestMapping("editCourse/{id}")
	public String editCourse(@PathVariable("id") int id, Model model) {
		Course c = service.getCoursebyId(id);
		model.addAttribute("course", c);
		return "editCourse";

	}
	@GetMapping("getCourseDetails/{id}")
	@ResponseBody
	public Course getCourseDetails(@PathVariable int id) {
		return service.getCoursebyId(id);
	}

}
