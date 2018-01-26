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
        // Get username and messages
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
        User userLogged = getLoggedUser(authentication);
        List<Message> messages = messageService.getMessagesByUser(userLogged);

        if (messages.contains(messageService.getMessageById(id))) {
            messageService.deleteMessageById(id);
            return "redirect:/messages";
        } else {
            return "redirect:/messages?error";
        }
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
    public String sendMessage(Model model, Message message, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }

        // TODO Make user cannot PM himself
        User receiver_username = userService.findByUser(message.getReceiver().getUsername());
        if (message.getReceiver().getId() != userLogged.getId()) {
            if (receiver_username != null) {
                System.out.println("recepient: " + receiver_username);
                messageService.save(new Message(message.getSubject(), message.getMessageText(), new Date(Calendar.getInstance().getTime().getTime()), userLogged, receiver_username));
                return "redirect:/messages?success";
            } else {
                model.addAttribute("recepienterror", true);
                return "redirect:/message-new?error";
            }
        } else {
            return "redirect:/messages?error";
        }
    }


    @GetMapping("/message-to")
    public String reply(Model model, Authentication authentication, Message message, @RequestParam Long id) {
        User userLogged = getLoggedUser(authentication);
        User recepient = userService.findById(id);

        if (recepient != null) {
            if (userLogged.getId() != id) {
                model.addAttribute("recepient",recepient);
                if (userLogged != null) {
                    model.addAttribute("loggedUser", userLogged);
                    model.addAttribute("message", new Message());
                }
                message.setReceiver(recepient);
                return "message-to";
            } else {
                return "redirect:messages?self";
            }
        } else {
            return "redirect:messages?erroruser";
        }
    }

    @PostMapping("/message-to")
    public String replyPost(Model model, Message message, Authentication authentication, @RequestParam Long id) {
        User userLogged = getLoggedUser(authentication);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }

        User target = userService.findById(id);
        if (target != null) {
            messageService.save(new Message(message.getSubject(), message.getMessageText(), new Date(Calendar.getInstance().getTime().getTime()), userLogged, target));
            return "redirect:/messages?success";
        } else {
            return "redirect:/message-to?error";
        }
    }
}