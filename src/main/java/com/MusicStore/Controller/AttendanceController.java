package com.MusicStore.Controller;
import java.util.List;

import com.MusicStore.Entity.*;
import com.MusicStore.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.MusicStore.Service.AttendanceService;
import com.MusicStore.Service.ScheduleService;
import com.MusicStore.Service.StudentService;

@Controller
public class AttendanceController {
	@Autowired
	private StudentService sservice;
	@Autowired
	private ScheduleService schservice;
	@Autowired
	private AttendanceService attendhservice;


	@GetMapping("/check_attendance")
	public String addCourse(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		List<Student> slist = sservice.getAllStudent();
		List<Schedule> schlist = schservice.getAllSchedule();
		model.addAttribute("student", slist);
		model.addAttribute("schedule", schlist);
		return "checkAttendance";
	}
	@RequestMapping("/dailyattendance")
	public String DailyAttendence (HttpSession session, Model model, @Param("scheduleID") String scheduleID) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		List<Student> list = sservice.listAllStudents(scheduleID);
		model.addAttribute("student", list);
		model.addAttribute("scheduleID", scheduleID);
		return "markAttendance";
	}

	@GetMapping("/show_attendance")
	public String attendance(HttpSession session, Model model) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");

		List<Schedule> sclist = null;

		if(currentUser.getUsercontrol().equals("1"))
			sclist = schservice.getAllSchedule();
		else if(currentUser.getUsercontrol().equals("2"))
			sclist = schservice.getScheduleByTeacherID(String.valueOf(currentUser.getId()));


		model.addAttribute("schedule", sclist);
		return "showAttendance";

	}

	@PostMapping("/saveattendance")
	public String saveAttendance(HttpSession session, @RequestParam ("schedule_id") int id,@RequestParam("att_date") String attDate,
            @RequestParam("statuses") List<String> statuses,
            @RequestParam("student_id") List<String> studentIds) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		attendhservice.saveAttendance(attDate, statuses, studentIds, id);
		return "redirect:/show_attendance";
	}

	@RequestMapping("deleteAttendace/{id}")
	public String deleteStudent(HttpSession session, @PathVariable("id") int id) {
		if(!Utils.checkIfAdminOrTeacherLoggedIn(session)) return "redirect:/login";

		attendhservice.deleteById(id);
		return "redirect:/show_attendance";

	}
	@GetMapping("/getAttendanceDetails/{id}")
	@ResponseBody
	public List<Attendance> getAttendanceDetails(@RequestParam("studentId") String studentId) {
	    // Implement logic to fetch attendance details from the database based on the selected studentId
	    List<Attendance> attendanceDetails = attendhservice.getAttendancebystudentId(studentId);
	    return attendanceDetails;
	}
}
