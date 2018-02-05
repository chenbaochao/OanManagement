package com.oan.management.controller;

import com.oan.management.model.User;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Oan on 1/02/2018.
 */
@Controller
public class AppSettingsController {
    @Autowired
    UserService userService;

    @GetMapping("/appsettings")
    public String getSettings(Model model, Authentication authentication, @RequestParam(value = "motivationText", required = false) String checkbox) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            if (checkbox == "on") {
                System.out.println("test yes");
            } else {
                System.out.println("test no");
            }
        }
        return "appsettings";
    }
}
