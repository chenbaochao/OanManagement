package com.oan.management.controller.authentication;

import com.oan.management.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Oan on 24/01/2018.
 */

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model, User user) {
        model.addAttribute("page-title", "Login");
        return "login";
    }
}
