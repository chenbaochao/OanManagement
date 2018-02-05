package com.oan.management.controller;

import com.oan.management.model.Bug;
import com.oan.management.model.User;
import com.oan.management.service.BugService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/admin")
    public String getAdminPanel(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "admin";
    }

    @GetMapping("/admin/bugreports")
    public String getBugReports(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Bug> bugs = bugService.findByFixedIsFalse();
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("bugs", bugs);
        }
        return "bugreports";
    }

}
