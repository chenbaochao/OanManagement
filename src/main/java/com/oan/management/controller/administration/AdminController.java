package com.oan.management.controller.administration;

import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.service.bug.BugService;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by Oan on 5/02/2018.
 */
@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    BugService bugService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/admin")
    public String getAdminPanel(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "admin";
    }

    @GetMapping("/admin/manageusers")
    public String getUserManagement(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<User> users = userService.findAll();

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("users", users);
        }
        return "manageusers";
    }

    @GetMapping("/admin/manageusers/{id}")
    public String getUserManagement(Model model, Authentication authentication, @PathVariable Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        List<User> users = userService.findAll();

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            if (id != null) {
                User paramUser = userService.findById(id);
                model.addAttribute("paramUser", paramUser);
                model.addAttribute("currentCountry", paramUser.getCountry());
            }
        }
        return "edituser";
    }

    @PostMapping("/admin/manageusers/{id}")
    public String editUser(Model model, User user, @PathVariable Long id) {
        User paramUser = userService.findById(id);
        // Basic
        paramUser.setFirstName(user.getFirstName());
        paramUser.setLastName(user.getLastName());
        paramUser.setCountry(user.getCountry());
        paramUser.setAge(user.getAge());
        // Social
        paramUser.setFacebook(user.getFacebook());
        paramUser.setSkype(user.getSkype());
        paramUser.setGithub(user.getGithub());
        // Unique
        paramUser.setEmail(user.getEmail());
        paramUser.setUsername(user.getUsername());

        userRepository.save(paramUser);
        return "redirect:/admin/manageusers/"+paramUser.getId();
    }
}
