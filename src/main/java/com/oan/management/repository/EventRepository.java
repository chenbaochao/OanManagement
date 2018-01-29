package com.oan.management.repository;

import com.oan.management.model.Event;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 26/01/2018.
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUser(User user);
    Event findById(Long id);
}
