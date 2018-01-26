package com.oan.management.service;

import com.oan.management.model.Event;
import com.oan.management.model.User;

import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */
public interface EventService {
    List<Event> findAllByUser(User user);
}
