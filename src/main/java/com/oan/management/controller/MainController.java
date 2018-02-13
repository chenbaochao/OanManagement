package com.oan.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oan.management.model.*;
import com.oan.management.repository.TaskRepository;
import com.oan.management.service.image.ImageService;
import com.oan.management.service.message.MessageService;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public String root(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Task> taskList = taskRepository.findByUserAndCompletedIsFalse(userLogged);
        // Get list of unread messages and get last message
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);
        if (unreadMessages.size() > 0) {
            Message lastMessage = unreadMessages.get(unreadMessages.size()-1);
            model.addAttribute("lastMessage", lastMessage);
        }
        // loggedUser and motivational texts
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            req.getSession().setAttribute("tasksLeftSession", taskList.size());
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
            if (taskList.size() == 0 && taskRepository.findByUserAndCompletedIsTrue(userLogged).size() == 0) {
                model.addAttribute("taskMotivation", "Hmm... What about giving yourself some tasks?");
            } else if (taskList.size() == 0 && taskRepository.findByUserAndCompletedIsTrue(userLogged).size() > 0) {
                model.addAttribute("taskMotivation", "Good job! You've completed all your tasks! Be proud about yourself.");
            }
            else if (taskList.size() == 1) {
                model.addAttribute("taskMotivation", "What, only 1 task? You should finish your work!");
            } else if (taskList.size() > 1 && taskList.size() <= 5){
                model.addAttribute("taskMotivation", "You only have "+taskList.size()+" tasks... That's not that much. Go complete them!");
            } else if (taskList.size() > 5 && taskList.size() <= 10){
                model.addAttribute("taskMotivation", "You've got some work there, "+taskList.size()+" tasks to complete. Try to complete as much as possible today.");
            } else if (taskList.size() > 10 ){
                model.addAttribute("taskMotivation", taskList.size()+" tasks! Let's see how many you could complete today! Proof yourself!");
            }
        }

        // JSON to Object mapper
        ObjectMapper mapper = new ObjectMapper();
        try {
            Quote quote = mapper.readValue(new URL("https://talaikis.com/api/quotes/random/"), Quote.class);
            model.addAttribute("quote", quote);
        } catch (IOException e) {
            // This is just added so I could work offline in the train
            Quote quote = new Quote("", "", "");
            model.addAttribute("quote", quote);
        }

        // Save profile picture for navigation bar
        Image avatar_of_id = imageService.findByTitle(userLogged.getId()+".png");
        if (avatar_of_id != null) {
            req.getSession().setAttribute("myAvatar", "/img"+avatar_of_id.getUrl());
        } else {
            req.getSession().setAttribute("myAvatar", "/img/avatar/0.png");
        }
        return "index";
    }
}
