package com.MusicStore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.MusicStore.Repository.CourseRepository;
import com.MusicStore.Repository.StudentRepository;
import com.MusicStore.Repository.TeacherRepository;


@Controller
public class HomeController {

	@Autowired
	private StudentRepository studrepo;
	@Autowired
	private TeacherRepository teacherrepo;
	@Autowired
	private CourseRepository courserepo;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/dash_board")
	public String dashboard(Model model) {
		 long studentCount = studrepo.count();
	        model.addAttribute("studentCount", studentCount);
	        long teacherCount = teacherrepo.count();
	        model.addAttribute("teacherCount", teacherCount);
	        long courseCount = courserepo.count();
	        model.addAttribute("courseCount", courseCount);
		return "dashboard";
	}

}
