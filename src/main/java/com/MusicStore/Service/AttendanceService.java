package com.MusicStore.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.MusicStore.Entity.Attendance;
import com.MusicStore.Repository.AttendanceRepository;

@Service
public class AttendanceService {
	@Autowired
	private AttendanceRepository atteRepo;

	public void save(@NonNull Attendance at) {
		atteRepo.save(at);
	}

	public List<Attendance> getAllAttendance() {
		return atteRepo.findAll();

	}

	public Attendance getAttendancebyId(int id) {
		return atteRepo.findById(id).get();
	}

	public void deleteById(int id) {
		atteRepo.deleteById(id);
	}
	 public void saveAttendance(@NonNull List<Attendance> attendanceList) {
		 atteRepo.saveAll(attendanceList);
	    }
	 public void saveAttendance(String attDate, List<String> statuses, List<String> studentIds, int id) {
	        for (int i = 0; i < statuses.size(); i++) {
	            String status = statuses.get(i);
	            String studentId = studentIds.get(i);
	            // Create an Attendance object and save it to the database
	            Attendance attendance = new Attendance();
	            attendance.setDate(attDate);
	            attendance.setStudent_id(studentId); 
	            attendance.setStatus(status);
	            attendance.setSchedule_id(id);
	            atteRepo.save(attendance);
	        }
	    }
	 public List<Attendance> getAttendancebystudentId(String Studentid) {
		 return atteRepo.attendence(Studentid);
		 
	 }
	
}
