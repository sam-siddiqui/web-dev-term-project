package com.MusicStore;

import com.MusicStore.Entity.*;
import jakarta.servlet.http.HttpSession;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean checkIfAdminOrTeacherLoggedIn(HttpSession session) {
        boolean isAuthorized = true;
        User currentUser = (User) session.getAttribute("user");
        if(currentUser == null) isAuthorized = false;
        else if(!(currentUser.getUsercontrol().equals("2") || currentUser.getUsercontrol().equals("1"))) isAuthorized = false;

        return isAuthorized;
    }

    public static boolean validateEmail(String email) {

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        return matcher.matches();
    }

    public static boolean isNumber(String input) {

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("\\d+(\\.\\d+)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(input);

        return matcher.matches();
    }

    public static String validateNewCourse(Course c) {
        String errorMessage = "";

        if(c.getCourse_name().length() < 2) {errorMessage += "Course Name too short! \n";};
        if(c.getDescription().length() < 10) {errorMessage += "Description too short! \n";};
        if(c.getDuration().isEmpty() || c.getDuration().length() > 2) {errorMessage += "Duration too long or empty! \n";}
        if(!isNumber(c.getDuration())) {errorMessage += "Duration invalid! \n";}
        if(!isNumber(c.getFees())) {errorMessage += "Fees invalid! \n";}

        return errorMessage;
    }

    public static String validateNewUser(User u) {
        String errorMessage = "";

        if(u.getFirstName().length() < 2) {errorMessage += "First Name too short! \n";};
        if(u.getLastName().length() < 2) {errorMessage += "Last Name too short! \n";};
        if(u.getEmail().length() < 2) {errorMessage += "Email too short! \n";}

        if(!validateEmail(u.getEmail())) {errorMessage += "Email Invalid! \n";}

        if(u.getPassword().length() < 2) {errorMessage += "Password too short! \n";};

        return errorMessage;
    }

    public static String validateNewTeacher(Teacher t) {
        String errorMessage = "";


        if(t.getName().length() < 2) {errorMessage += "Name too short! \n";};
        if(t.getEmail().length() < 2) {errorMessage += "Email too short! \n";}
        if(t.getPhone_number().length() < 10) {errorMessage += "Phone Number too short! \n";}

        if(!validateEmail(t.getEmail())) {errorMessage += "Email Invalid! \n";}

        return errorMessage;
    }

    public static String validateNewStudent(Student s) {
        String errorMessage = "";

        if(s.getName().length() < 2) {errorMessage += "Name too short! \n";};
        if(s.getEmail().length() < 2) {errorMessage += "Email too short! \n";}
        if(s.getAddress().length() < 5) {errorMessage += "Address too short! \n";}
        if(s.getPhone_number().length() < 10) {errorMessage += "Phone Number too short! \n";}

        if(!validateEmail(s.getEmail())) {errorMessage += "Email Invalid! \n";}

        return errorMessage;
    }

    public static String validateNewSchedule(Show_Schedule s) {
        String errorMessage = "";
        ArrayList<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        if(!daysOfWeek.contains(s.getClass_day())) {errorMessage += "Not a valid day! \n";}
        if(s.getSchedule_name().length() < 3) {errorMessage += "Schedule name too short! \n";}

        return errorMessage;
    }

    public static ArrayList<String> getDaysOfWeek() {
        ArrayList<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        return daysOfWeek;
    }

    public static String validateNewSchedule(Schedule s) {
        String errorMessage = "";
        ArrayList<String> daysOfWeek = getDaysOfWeek();

        if(!daysOfWeek.contains(s.getClass_day())) {errorMessage += "Not a valid day! \n";}
        if(s.getSchedule_name().length() < 3) {errorMessage += "Schedule name too short! \n";}

        return errorMessage;
    }

    public static boolean checkIfAdminOrStudentLoggedIn(HttpSession session) {
        boolean isAuthorized = true;
        User currentUser = (User) session.getAttribute("user");
        if(currentUser == null) isAuthorized = false;
        else if(!(currentUser.getUsercontrol().equals("3") || currentUser.getUsercontrol().equals("1"))) isAuthorized = false;

        return isAuthorized;
    }

    public static ArrayList<String> getUserRoles() {
        ArrayList<String> userRoles = new ArrayList<>();
        userRoles.add("Admin");
        userRoles.add("Teacher");
        userRoles.add("Student");

        return userRoles;
    }

    public static boolean checkIfAdminLoggedIn(HttpSession session) {
        boolean isAuthorized = true;
        User currentUser = (User) session.getAttribute("user");
        if(currentUser == null) isAuthorized = false;
        else if(!(currentUser.getUsercontrol().equals("1"))) isAuthorized = false;

        return isAuthorized;
    }

}
