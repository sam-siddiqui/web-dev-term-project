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
import org.springframework.web.servlet.ModelAndView;
import com.MusicStore.Entity.Teacher;
import com.MusicStore.Service.TeacherService;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService service;

	@GetMapping("/add_teacher")
	public String addTeacher() {
		return "addTeacher";
	}

	@GetMapping("/show_teacher")
	public ModelAndView getAllTeacher() {
		List<Teacher> list = service.getAllTeacher();
		ModelAndView teacher = new ModelAndView();
		teacher.setViewName("showTeacher");
		teacher.addObject("teacher", list);
		return teacher;
	}

	@PostMapping("/saveteacher")
	public String addTeacher(@ModelAttribute @NonNull Teacher t) {
		service.save(t);
		return "redirect:/show_teacher";
	}

	@RequestMapping("deleteTeacher/{id}")
	public String deleteTeacher(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/show_teacher";

	}

	@RequestMapping("editTeacher/{id}")
	public String editTeacher(@PathVariable("id") int id, Model model) {
		Teacher t = service.getTeacherbyId(id);
		model.addAttribute("teacher", t);
		return "editTeacher";

	}

	@RequestMapping("/searchteacher")
	public String searchTeacher(Model model, @Param("keyword") String keyword) {
		List<Teacher> list = service.listAll(keyword);
		model.addAttribute("teacher", list);
		model.addAttribute("keyword", keyword);
		return "showTeacher";
	}
}
