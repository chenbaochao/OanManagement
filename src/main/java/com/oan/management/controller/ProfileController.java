package com.oan.management.controller;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String root(Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());

        if (userLogged != null) {
            model.addAttribute("firstName", userLogged.getFirstName());
            model.addAttribute("lastName", userLogged.getLastName());
            model.addAttribute("email", userLogged.getEmail());
            model.addAttribute("roles", userLogged.getRoles());
            model.addAttribute("id", userLogged.getId());
        }

        return "profile";
    }
}
