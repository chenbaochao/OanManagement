package com.oan.management.controller;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class CalendarController {
    @Autowired
    private UserService userService;

    @GetMapping("/calendar")
    public String calendar(Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "calendar";
    }
}
