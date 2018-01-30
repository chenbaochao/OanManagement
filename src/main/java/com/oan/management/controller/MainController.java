package com.oan.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oan.management.model.Message;
import com.oan.management.model.Quote;
import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.repository.TaskRepository;
import com.oan.management.service.MessageService;
import com.oan.management.service.UserService;
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

    @GetMapping("/")
    public String root(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Task> taskList = taskRepository.findByUserAndCompletedIsFalse(userLogged);
        // Get list of unread messages
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);

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
                model.addAttribute("taskMotivation", "What? Only 1 task? You should finish your work!");
            } else if (taskList.size() > 1 && taskList.size() <= 5){
                model.addAttribute("taskMotivation", "You only have "+taskList.size()+" tasks... That's not that much. Go complete them!");
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
        return "index";
    }
}
