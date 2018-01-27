package com.oan.management.service;

import com.oan.management.model.Task;
import com.oan.management.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by Oan on 24/01/2018.
 */
public interface TaskService {
    List<Task> findByUser(User user);
    Task findById(Long id);
    Task getById(Long id);
    void deleteTaskById(Long id);
    Task completeTaskById(Long id);
    Task uncompleteTaskById(Long id);
    Task save(Task task);
    Task getOne(Long id);
    Task editById(Long id, String desc, Date date, boolean completed);
}
