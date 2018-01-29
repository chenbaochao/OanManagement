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
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Oan on 29/01/2018.
 */

@Controller
public class BugController {
    @Autowired
    public UserService userService;

    @Autowired
    public BugService bugService;

    @GetMapping("/report-bug")
    public String reportBugPage(Authentication authentication, Model model) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("bug", new Bug());
        }
        return "/report-bug";
    }

    @PostMapping("/report-bug")
    public String reportBugSubmit(Authentication authentication, Bug bug, Model model) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged.getRoles().contains("ROLE_USER")) {
            if (userLogged.getBugsReported() < 10) {
                bugService.save(new Bug(userLogged.getUsername(), bug.getDescription(), new Date(Calendar.getInstance().getTime().getTime())));
                userService.addBugReport(userLogged);
                return "redirect:/report-bug?reported";
            } else {
                return "redirect:/report-bug?failed";
            }
        } else {
            bugService.save(new Bug(userLogged.getUsername(), bug.getDescription(), new Date(Calendar.getInstance().getTime().getTime())));
            return "redirect:/report-bug?reported";
        }
    }
}
