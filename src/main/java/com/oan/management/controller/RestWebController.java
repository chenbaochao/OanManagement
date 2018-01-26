package com.oan.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oan.management.model.Event;
import com.oan.management.model.User;
import com.oan.management.service.EventService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */

@RestController
@RequestMapping("/api/event")
public class RestWebController {
    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String getEvents(Authentication authentication) {
        String jsonMessage = null;
        try {
      /*      List<Event> events = new ArrayList<Event>();
            Event event = new Event();
            event.setTitle("Test");
            event.setStart("2018-01-26");
            event.setEnd("2018-01-28");
            events.add(event);*/

            User userLogged = userService.findByUser(authentication.getName());
            List<Event> events = eventService.findAllByUser(userLogged);

            ObjectMapper mapper = new ObjectMapper();
            jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return jsonMessage;
    }
}
