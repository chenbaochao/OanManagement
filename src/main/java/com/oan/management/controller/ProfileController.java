package com.oan.management.controller;

import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.service.MessageService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/profile")
    public String root(HttpServletRequest req, Model model, Authentication authentication, @RequestParam(required = false) Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        User paramUser = userService.findById(id);
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);
        // For navbar
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }

        // Get user data to web
        if (id != null) {
            model.addAttribute("paramUser", paramUser);
        } else {
            model.addAttribute("paramUser", userLogged);
            System.out.println("TEST");
            return "redirect:/profile?id=" + userLogged.getId();
        }
        return "profile";
    }
}
