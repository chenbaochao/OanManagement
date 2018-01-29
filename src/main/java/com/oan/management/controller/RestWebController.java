package com.oan.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oan.management.model.Event;
import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.service.EventService;
import com.oan.management.service.TaskService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
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

    @Autowired
    TaskService taskService;

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
            List<Task> tasks = taskService.findByUserAndCompletedIsFalse(userLogged);

            for (Task task : tasks) {
                events.add(new Event(task.getDescription(), "To-Do: "+task.getDescription(), task.getTargetDate(), new Date(task.getTargetDate().getTime() + (1000 * 60 * 60 * 24)), userLogged));
            }

            ObjectMapper mapper = new ObjectMapper();
            jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return jsonMessage;
    }
}
