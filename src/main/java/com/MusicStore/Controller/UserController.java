package com.MusicStore.Controller;

import com.MusicStore.Entity.Student;
import com.MusicStore.Entity.Teacher;
import com.MusicStore.Entity.User;
import com.MusicStore.Repository.UserRepository;
import com.MusicStore.Service.UserService;
import com.MusicStore.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService uservice;

    @GetMapping("/show_users")
    public String getAllUsers(HttpSession session, Model model) {
        if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";
        List<User> userslist = uservice.getAllUser();

        User currentUser = (User) session.getAttribute("user");

        long currentAdminID = currentUser.getId();
        model.addAttribute("username", currentUser.getFullName());
        model.addAttribute("allUsers", userslist);
        model.addAttribute("currentAdminID", currentAdminID);


        return "showUser";
    }

    @GetMapping("/addUser")
    public String addUserPage(HttpSession session, Model model) {
        if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

        if(session.getAttribute("error") != null)
            model.addAttribute("error", session.getAttribute("error"));


        User u = null;
        if(session.getAttribute("error") != null)
            u = (User) session.getAttribute("oldValuesU");
        if(session.getAttribute("oldValuesU") != null)
            model.addAttribute("user", u);

        model.addAttribute("allRoles", Utils.getUserRoles());

        session.setAttribute("error", null);
        return "addUser";
    }

    @PostMapping("/addUser")
    public String createUser(HttpSession session, @ModelAttribute @NonNull User u, Model model) {
        if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

        String errorMessage = Utils.validateNewUser(u);
        boolean isValidUserDetails = errorMessage.isEmpty();

        if(!isValidUserDetails) {
            session.setAttribute("error", errorMessage);
            session.setAttribute("oldDetailsU", u);
            return "redirect:/updateUser/";
        };

        uservice.save(u);

        model.addAttribute("message", "User Created Successfully");

        return "redirect:/show_users";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(HttpSession session, @PathVariable("id") int id, Model model){
        if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";
        if(session.getAttribute("error") != null)
            model.addAttribute("error", session.getAttribute("error"));

        User u = null;
        if(session.getAttribute("error") != null)
            u = (User) session.getAttribute("oldValuesU");
        else
            u = uservice.getUserbyId(id);

        session.setAttribute("error", null);
        model.addAttribute("user", u);
        model.addAttribute("allRoles", Utils.getUserRoles());
        return "editUser";
    }

    @PostMapping("/editUser/{id}")
    public String updateUser(HttpSession session, @PathVariable("id") int id, @ModelAttribute @NonNull User u, Model model) {
        if (!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

        String errorMessage = Utils.validateNewUser(u);
        boolean isValidUserDetails = errorMessage.isEmpty();

        if(!isValidUserDetails) {
            session.setAttribute("error", errorMessage);
            session.setAttribute("oldDetailsU", u);
            return "redirect:/updateUser/" + id;
        };

        uservice.save(u);
        session.setAttribute("oldDetailsU", null);
        session.setAttribute("error", null);

        model.addAttribute("message", "User Updated Successfully");
        return "redirect:/show_users";
    }

    @GetMapping("/profile_settings")
    public String getProfilePage(HttpServletRequest request, Model model) {
        if(request.getSession(false) == null) return "redirect:/login";

        HttpSession session = request.getSession();
        User currentUser = null;
        if(session.getAttribute("oldDetailsU") == null)
            currentUser = (User) session.getAttribute("user");
        else
            currentUser = (User) session.getAttribute("oldDetailsU");

        if(currentUser == null) return "redirect:/login";

        if(session.getAttribute("error") != null)
            model.addAttribute("error", session.getAttribute("error"));

        model.addAttribute("currentUser", currentUser);

        session.setAttribute("error", null);
        return "editProfile";
    }

    @PostMapping("/updateUserProfile")
    public String updateUser(HttpServletRequest request, Model model, @ModelAttribute @NonNull User u) {
        if(request.getSession(false) == null) return "redirect:/login";

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if(currentUser == null) return "redirect:/login";

        String errorMessage = Utils.validateNewUser(u);
        boolean isValidUserDetails = errorMessage.isEmpty();

        if(!isValidUserDetails) {
            session.setAttribute("error", errorMessage);
            session.setAttribute("oldDetailsU", u);
            return "redirect:/profile_settings";
        };

        uservice.save(u);
        User updatedUser = uservice.getUserbyId(Math.toIntExact(currentUser.getId()));
        session.setAttribute("oldDetailsU", null);
        session.setAttribute("error", null);
        session.setAttribute("user", updatedUser);

        model.addAttribute("message", "User Updated Successfully");
        return "redirect:/dash_board";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteUser(HttpSession session, @PathVariable("id") int id) {
        if(!Utils.checkIfAdminLoggedIn(session)) return "redirect:/login";

        uservice.deleteById(id);
        return "redirect:/show_users";
    }

}
