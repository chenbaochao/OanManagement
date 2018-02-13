package com.oan.management.controller.calendar;

import com.oan.management.model.Event;
import com.oan.management.model.Message;
import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.service.calendar.EventService;
import com.oan.management.service.message.MessageService;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserRepository userRepository;

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

        if (!event.getTitle().isEmpty()) {
            eventService.save(new Event(event.getTitle(), event.getDescription(), event.getStart(), event.getEnd(), userLogged, event.getBackgroundColor(), event.getBackgroundColor(), true));
        }

        userLogged.setEventsCreated(userLogged.getEventsCreated()+1);
        userRepository.save(userLogged);
        return "redirect:/calendar";
    }

    @GetMapping("/calendar-delete")
    public String deleteEvent(Authentication authentication, @RequestParam Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        eventService.delete(eventService.findById(id));
        return "redirect:/calendar";
    }

    @GetMapping("/calendar-update")
    public String updateEvent(Authentication authentication, @RequestParam String title, @RequestParam String start, @RequestParam String end, @RequestParam Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        eventService.editById(id, title, start, end);
        return "redirect:/calendar";
    }

    @PostMapping("calendar-update")
    public String updateEventPost(Event event) {
        Event updateEvent = eventService.findById(event.getId());
        updateEvent.setTitle(event.getTitle());
        updateEvent.setDescription(event.getDescription());
        updateEvent.setBackgroundColor(event.getBackgroundColor());
        updateEvent.setBorderColor(event.getBackgroundColor());
        eventService.save(updateEvent);
        return "redirect:/calendar";
    }

}
