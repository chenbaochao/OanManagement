package com.oan.management.controller;

import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.repository.TaskRepository;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskService;

    @GetMapping("/")
    public String root(Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Task> taskList = taskService.findByUser(userLogged);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasksLeft", taskList.size());
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, User user) {
        return "login";
    }
}
