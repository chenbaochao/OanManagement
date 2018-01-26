package com.oan.management.service;

import com.oan.management.model.Event;
import com.oan.management.model.User;
import com.oan.management.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> findAllByUser(User user) {
        return eventRepository.findAllByUser(user);
    }
}
