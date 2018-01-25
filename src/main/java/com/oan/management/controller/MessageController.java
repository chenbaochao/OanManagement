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

/**
 * Created by Oan on 25/01/2018.
 */

@Controller
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    public User getLoggedUser(Authentication authentication) {
        return userService.findByEmail(authentication.getName());
    }

    @GetMapping("/messages")
    public String getInbox(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Message> messages = messageService.getMessagesByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("messages", messages);
        }
        return "messages";
    }

    @GetMapping("/message")
    public String openMessage(HttpServletRequest req, Model model, Authentication authentication, @RequestParam Long id) {
        User userLogged = getLoggedUser(authentication);
        List<Message> messages = messageService.getMessagesByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("messages", messages);
        }
        if (id != null) {
            model.addAttribute("message", messageService.getMessageById(id));
            return "message";
        }
        else {
            return "messages";
        }
    }
}