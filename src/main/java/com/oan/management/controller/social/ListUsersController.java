package com.oan.management.controller.social;

import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.service.MessageService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Oan on 28/01/2018.
 */

@Controller
public class ListUsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    public User getLoggedUser(Authentication authentication) {
        return userService.findByUser(authentication.getName());
    }

    @GetMapping("/users")
    public String listUsers(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<User> users = userService.findAll();
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("users", users);
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }
        return "users";
    }
}
