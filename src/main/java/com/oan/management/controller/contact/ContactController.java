package com.oan.management.controller.contact;

import com.oan.management.model.Contact;
import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.service.ContactService;
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
import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class ContactController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ContactService contactService;

    public User getLoggedUser(Authentication authentication) {
        return userService.findByUser(authentication.getName());
    }

    @GetMapping("/contacts")
    public String contactlist(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Contact> contactList = contactService.findByUser(userLogged);
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }
        return "contacts";
    }

    @GetMapping("/contact-new")
    public String newContactPage(Model model, Contact contact, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        model.addAttribute("contact", new Contact());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "contact-new";
    }

    @PostMapping("/contact-new")
    public String newcontact(Model model, Contact contact, BindingResult result, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
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
        User userLogged = getLoggedUser(authentication);
        List<Contact> contactList = contactService.findByUser(userLogged);
        if (contactList.contains(contactService.getOne(id))) {
            contactService.deleteById(id);
            return "redirect:/contacts?deleted";
        } else {
            return "redirect:/contacts?notfound";
        }
    }

    @GetMapping("/contacts-edit")
    public String editContactOnScreen(Model model, Contact contact, @RequestParam Long id, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Contact> contactList = contactService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("contacts", contactList);
        }
        // Check if its the user's contact
        if (contactList.contains(contactService.getOne(id))) {
            model.addAttribute("contact", contactService.getOne(id));
            return "/contacts-edit";
        } else {
            return "redirect:/contacts?notfound";
        }
    }

    @PostMapping("/contacts-edit")
    public String editContact(Model model, Contact contact, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
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
