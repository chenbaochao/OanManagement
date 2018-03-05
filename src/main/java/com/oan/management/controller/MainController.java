package com.oan.management.controller;

import com.oan.management.model.*;
import com.oan.management.repository.TaskRepository;
import com.oan.management.service.image.ImageService;
import com.oan.management.service.message.MessageService;
import com.oan.management.service.rank.RankService;
import com.oan.management.service.task.TaskService;
import com.oan.management.service.user.UserService;
import com.oan.management.utility.CustomTimeMessage;
import com.oan.management.utility.RandomQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller for the Home (index) page with widgets which can be customized by the /appsettings page
 */

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RankService rankService;

    @GetMapping("/")
    public String root(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Task> taskList = taskRepository.findByUserAndCompletedIsFalseAndApprovedIsTrue(userLogged);
        // Get list of unread messages and get last message
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);
        if (unreadMessages.size() > 0) {
            Message lastMessage = unreadMessages.get(unreadMessages.size()-1);
            model.addAttribute("lastMessage", lastMessage);
        }
        // loggedUser and motivational texts
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            // Get the custom greeting by time
            CustomTimeMessage customTimeMessage = new CustomTimeMessage();
            model.addAttribute("timeGreeting", customTimeMessage.getMessage());
            // Tasks and unread messages
            req.getSession().setAttribute("tasksLeft", taskList.size());
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
            // Check for pending tasks
            List<Task> pendingTasks = taskService.findByUserAndApprovedIsFalse(userLogged);
            req.getSession().setAttribute("pendingTasksCount", pendingTasks.size());
            // Motivational messages
            String motivationMessage = taskService.getMotivationalMessage(taskList, userLogged);
            model.addAttribute("taskMotivation", motivationMessage);
        }
        // JSON to Object mapper
        RandomQuote randomQuote = new RandomQuote("https://talaikis.com/api/quotes/random/");
        model.addAttribute("quote", randomQuote.getQuote(randomQuote.getUrl()));
        // Get user avatar
        Image avatar = imageService.getUserImage(userLogged);
        req.getSession().setAttribute("myAvatar", "/img/"+avatar.getUrl());
        // Get user rank
        Rank userRank = rankService.getUserRank(userLogged);
        model.addAttribute("userRank", userRank);
        return "index";
    }
}
