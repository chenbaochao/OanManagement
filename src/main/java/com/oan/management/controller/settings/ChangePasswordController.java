package com.oan.management.controller.settings;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Oan on 1/02/2018.
 */
@Controller
public class ChangePasswordController {
    @Autowired
    UserService userService;

    @GetMapping("/changepassword")
    public String changePasswordPage(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "changepassword";
    }
}
