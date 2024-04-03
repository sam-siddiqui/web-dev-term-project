package com.MusicStore.Controller;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.MusicStore.Entity.Attendance;
import com.MusicStore.Entity.Schedule;
import com.MusicStore.Entity.Student;
import com.MusicStore.Repository.AttendanceRepository;
import com.MusicStore.Service.AttendanceService;
import com.MusicStore.Service.ScheduleService;
import com.MusicStore.Service.StudentService;
import com.MusicStore.Service.UserService;

@Controller
public class AttendanceController {
	@Autowired
	private StudentService sservice;
	@Autowired
	private ScheduleService schservice;
	@Autowired
	private AttendanceService attendhservice;
	@Autowired
	private UserService userservice;


	@GetMapping("/add_attendance")
	public String addCourse(Model model) {
		List<Student> slist = sservice.getAllStudent();
		List<Schedule> schlist = schservice.getAllSchedule();
		model.addAttribute("student", slist);
		model.addAttribute("schedule", schlist);
		return "addAttendance";
	}
	@RequestMapping("/dailyattendance")
	public String DailyAttendence (Model model, @Param("scheduleID") String scheduleID) {
		List<Student> list = sservice.listAllStudents(scheduleID);
		model.addAttribute("student", list);
		model.addAttribute("scheduleID", scheduleID);
		return "markAttendance";
	}

	@GetMapping("/attendence")
	public String attendance(Model model) {
		List<Student> slist = sservice.getAllStudent();
		List<Schedule> schlist = schservice.getAllSchedule();
		model.addAttribute("student", slist);
		model.addAttribute("schedule", schlist);
		return "showAttendance";

	}

	@PostMapping("/saveattendance")
	public String saveAttendance(@RequestParam ("schedule_id") int id,@RequestParam("att_date") String attDate,
            @RequestParam("statuses") List<String> statuses,
            @RequestParam("student_id") List<String> studentIds) {
		attendhservice.saveAttendance(attDate, statuses, studentIds, id);
		return "redirect:/attendence";
	}

	@RequestMapping("deleteAttendace/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
		attendhservice.deleteById(id);
		return "redirect:/attendence";

	}
}
