package com.oan.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oan.management.model.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */

@RestController
@RequestMapping("/api/event")
public class RestWebController {
    @GetMapping("/all")
    public String getEvents() {
        String jsonMessage = null;
        try {
            List<Event> events = new ArrayList<Event>();
            Event event = new Event();
            event.setTitle("Test");
            event.setStart("2018-01-26");
            event.setEnd("2018-01-28");
            events.add(event);

            ObjectMapper mapper = new ObjectMapper();
            jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return jsonMessage;
    }
}
