package com.oan.management.controller;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String root(Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, User user) {
        return "login";
    }
}
