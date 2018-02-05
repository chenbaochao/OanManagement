package com.oan.management.service;

import com.oan.management.model.Event;
import com.oan.management.model.User;

import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */
public interface EventService {
    List<Event> findAllByUser(User user);
    Event save(Event event);
    void delete(Event event);
    Event findById(Long id);
    Event editById(Long id, String title, String start, String end);
}
