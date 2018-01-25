package com.oan.management.controller;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String root(Model model, Authentication authentication, @RequestParam(required = false) Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        User paramUser = userService.findById(id);
        System.out.println("TEST: "+paramUser);
        // For navbar
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }

        // Get user data to web
        if (id != null) {
            model.addAttribute("paramUser", paramUser);
        } else {
            model.addAttribute("paramUser", userLogged);
            System.out.println("TEST");
            return "redirect:/profile?id="+userLogged.getId();
        }
        return "profile";
    }
}
