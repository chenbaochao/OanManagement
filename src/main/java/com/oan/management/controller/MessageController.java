package com.oan.management.controller;

import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.service.MessageService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Calendar;
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
        return userService.findByUser(authentication.getName());
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
    public String openMessage(Model model, Authentication authentication, @RequestParam Long id) {
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
            return "redirect:/messages";
        }
    }

    @GetMapping("/message-delete")
    public String deleteMessage(Model model, Authentication authentication, @RequestParam Long id) {
        messageService.deleteMessageById(id);
        return "redirect:/messages";
    }

    @GetMapping("/message-new")
    public String newMessage(Model model, Authentication authentication, Message message) {
        User userLogged = getLoggedUser(authentication);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("message", new Message());
        }
        return "message-new";
    }

    @PostMapping("/message-new")
    public String sendMessage(Model model, Message message, BindingResult bindingResult, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        User receiver_username = userService.findByUser(message.getReceiver().getUsername());

        if (receiver_username != null) {
            System.out.println("recepient: "+receiver_username);
            messageService.save(new Message(message.getSubject(), message.getMessageText(), new Date(Calendar.getInstance().getTime().getTime()), userLogged, receiver_username));
            return "redirect:/messages?success";
        } else {
            bindingResult.rejectValue("receiver.username", null,"This user doesn't exist");
            model.addAttribute("recepienterror", true);
            return "redirect:/message-new?error";
        }
    }
}