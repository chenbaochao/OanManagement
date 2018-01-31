package com.oan.management.controller;

import com.oan.management.model.Image;
import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.service.ImageService;
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
import java.util.Comparator;
import java.util.List;

/**
 * Created by Oan on 25/01/2018.
 */

@Controller
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser(Authentication authentication) {
        return userService.findByUser(authentication.getName());
    }

    @GetMapping("/messages")
    public String getInbox(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Message> messages = messageService.getMessagesByUser(userLogged);
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);
        // Sort by id, last id's last (newest messages on top)
        messages.sort(Comparator.comparing(Message::getId).reversed());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("messages", messages);
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }
        return "messages";
    }

    @GetMapping("/message")
    public String openMessage(HttpServletRequest req, Model model, Authentication authentication, @RequestParam Long id) {
        User userLogged = getLoggedUser(authentication);
        List<Message> messages = messageService.getMessagesByUser(userLogged);
        // Get username and messages
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("messages", messages);
        }

        if (id != null) {
            Message msg = messageService.getMessageById(id);
            model.addAttribute("message", msg);
            msg.setOpened(1);
            Image imageUrlOfSender = imageService.findFirstByTitle(msg.getSender().getId()+".png");
            if (imageUrlOfSender != null) {
                model.addAttribute("avatar", "/img"+imageUrlOfSender.getUrl());
            } else {
                model.addAttribute("avatar", "/img/avatar/0.png");
            }
            messageService.save(msg);
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
                // Update statistics
                userLogged.setMessagesSent(userLogged.getMessagesSent()+1);
                User receiver_user = userService.findByUser(message.getReceiver().getUsername());
                receiver_user.setMessagesReceived(receiver_user.getMessagesReceived()+1);
                userRepository.save(userLogged);
                userRepository.save(receiver_user);
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
            // Update statistics
            userLogged.setMessagesSent(userLogged.getMessagesSent()+1);
            target.setMessagesReceived(target.getMessagesReceived()+1);
            userRepository.save(target);
            userRepository.save(userLogged);
            return "redirect:/messages?success";
        } else {
            return "redirect:/message-to?error";
        }
    }

    @GetMapping("message-unread")
    public String unreadMessage(@RequestParam Long id) {
        Message msg = messageService.getMessageById(id);
        msg.setOpened(0);
        messageService.save(msg);
        return "redirect:/messages";
    }
}