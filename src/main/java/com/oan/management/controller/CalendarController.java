package com.oan.management.controller;

import com.oan.management.model.Event;
import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.service.EventService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class CalendarController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/calendar")
    public String calendar(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("event", new Event());
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }
        return "calendar";
    }

    @PostMapping("/calendar")
    public String addEvent(Model model, Event event, Authentication authentication, @RequestParam("start") String startdate, @RequestParam("end") String enddate) {
        User userLogged = userService.findByUser(authentication.getName());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.sql.Date sqlStartDate = new java.sql.Date(format.parse(startdate).getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(format.parse(enddate).getTime());
            event.setStart(sqlStartDate);
            event.setEnd(sqlEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventService.save(new Event(event.getTitle(), event.getDescription(), event.getStart(), event.getEnd(), userLogged));
        return "redirect:/calendar";
    }

    @GetMapping("/calendar-delete")
    public String deleteEvent(Authentication authentication, @RequestParam Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        eventService.delete(eventService.findById(id));
        return "redirect:/calendar";
    }

    @GetMapping("/calendar-update")
    public String updateEvent(Authentication authentication, @RequestParam String title, @RequestParam Date start, @RequestParam Date end, @RequestParam Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        eventService.editById(id, title, start, end);
        return "redirect:/calendar";
    }

}
