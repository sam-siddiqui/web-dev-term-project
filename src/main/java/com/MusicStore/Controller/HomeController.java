package com.MusicStore.Controller;

import com.MusicStore.Entity.*;
import com.MusicStore.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

	@Autowired
	private StudentRepository studrepo;
	@Autowired
	private ScheduleRepository schedulerepo;
	@Autowired
	private BillingRepository billrepo;
	@Autowired
	private TeacherRepository teacherrepo;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private AttendanceRepository attendancerepo;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/dash_board")
	public String dashboard(HttpServletRequest request, Model model) {
		if(request.getSession(false) == null) return "redirect:/login";

		HttpSession session = request.getSession();
		if(!((boolean) session.getAttribute("userLoggedIn"))) return "redirect:/login";

		User currentUser = (User) session.getAttribute("user");
		if(currentUser == null) return "redirect:/login";

		String currentUserName = currentUser.getFullName();
		String currentUserRole = currentUser.getUsercontrol();
		model.addAttribute("username", currentUserName);
		model.addAttribute("userrole", currentUserRole);

		if(currentUserRole.equals("3")) {
			return getStudentDashboard(request, session, model);
		}

		long studentCount = -1;
		long teacherCount = -1;
		long courseCount = -1;
		long usersNeedingApproval = -1;

		if(currentUserRole.equals("1")) {
			studentCount = studrepo.count();
			teacherCount = teacherrepo.count();
			courseCount = courserepo.count();
			usersNeedingApproval = userrepo.usersNeedingApproval().size();
		}

		if(currentUserRole.equals("2")) {
			long currentTeacherID = currentUser.getId();
			studentCount = studrepo.getStudentsByTeacherID(String.valueOf(currentTeacherID)).size();
			courseCount = courserepo.getCoursesByTeacherID(String.valueOf(currentTeacherID)).size();
		}

		 model.addAttribute("studentCount", studentCount);
	     model.addAttribute("teacherCount", teacherCount);
	     model.addAttribute("courseCount", courseCount);

		 if(currentUserRole.equals("1")) model.addAttribute("usersNeedingApprovalCount", usersNeedingApproval);
		 return "dashboard";
	}

	private String getStudentDashboard(HttpServletRequest request, HttpSession session, Model model) {
		User currentUser = (User) session.getAttribute("user");
		long currentStudentID = currentUser.getId();
		long currentStudentScheduleID = studrepo.getStudentByStudentID(String.valueOf(currentStudentID)).getSchedule_id();

		List<Course> coursesList = null;
		List<Teacher> teachersList = null;
		List<Billing> pendingBillsList = null;
		List<Schedule> upcomingSchedulesList = null;
		List<Attendance> totalAbsentClasses = null;

		long teacherCount = -1;
		long courseCount = -1;
		long absentCount = -1;
		long dueBillCount = -1;

		coursesList = courserepo.getCoursesByStudentScheduleID(String.valueOf(currentStudentScheduleID));
		teachersList = teacherrepo.getTeachersByStudentScheduleID(String.valueOf(currentStudentScheduleID));
		pendingBillsList = billrepo.findPendingByStudentID(String.valueOf(currentStudentID), "Pending");
		totalAbsentClasses = attendancerepo.getAttendanceByStudentID(String.valueOf(currentStudentID), "absent");
		upcomingSchedulesList = schedulerepo.getScheduleByStudentScheduleID(String.valueOf(currentStudentScheduleID));

		teacherCount = teachersList.size();
		courseCount = coursesList.size();
		absentCount = totalAbsentClasses.size();
		dueBillCount = pendingBillsList.size();


		model.addAttribute("teacherCount", teacherCount);
		model.addAttribute("courseCount", courseCount);
		model.addAttribute("absentCount", absentCount);
		model.addAttribute("dueBillsCount", dueBillCount);
		model.addAttribute("currentTeachers", teachersList);
		model.addAttribute("currentCourses", coursesList);
		model.addAttribute("dueBills", pendingBillsList);
		model.addAttribute("upcomingScheduledClasses", upcomingSchedulesList);

		return "studentDashboard";
	}

}
