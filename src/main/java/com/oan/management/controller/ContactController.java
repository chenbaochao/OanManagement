package com.oan.management.controller;

import com.oan.management.model.Contact;
import com.oan.management.model.User;
import com.oan.management.repository.ContactRepository;
import com.oan.management.service.ContactService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class ContactController {
    @Autowired
    private UserService userService;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts")
    public String contactlist(Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        model.addAttribute("contact", new Contact());
        List<Contact> contactList = contactService.findByUser(userLogged);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
        }

        return "contacts";
    }

    @PostMapping("/contacts")
    public String newcontact(Model model, Contact contact, BindingResult result, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Contact> contactList = contactService.findByUser(userLogged);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
        }

        contactService.save(new Contact(userLogged, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getMobileNumber(), contact.getNotes(), contact.getAddress()));


        return "redirect:/contacts";
    }

    @GetMapping("/contacts-delete")
    public String deleteContact(Model model, Contact contact, @RequestParam Long id, Authentication authentication) {
        contactService.deleteById(id);
        return "redirect:/contacts";
    }

    @GetMapping("/contacts-edit")
    public String editContactOnScreen(Model model, Contact contact, @RequestParam Long id, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Contact> contactList = contactService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
        }

        model.addAttribute("contact", contactService.getOne(id));
        return "/contacts-edit";
    }

    @PostMapping("/contacts-edit")
    public String editContact(Model model, Contact contact, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Contact> contactList = contactService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
        }

        contactService.save(new Contact(userLogged, contact.getFirstName(), contact.getLastName(), contact.getEmail(),
                contact.getMobileNumber(), contact.getNotes(), contact.getAddress()));
        contactService.deleteById(contact.getId());

        return "redirect:contacts";
    }

}
