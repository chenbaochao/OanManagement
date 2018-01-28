package com.oan.management.service;

import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Created by Oan on 24/01/2018.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.getById(id);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.delete(taskRepository.findById(id));
    }

    @Override
    public Task completeTaskById(Long id) {
        Task task = taskRepository.findById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getOne(Long id) {
        return taskRepository.getById(id);
    }

    @Override
    public Task editById(Long id, String desc, Date date, boolean completed) {
        Task task = taskRepository.getById(id);
        task.setDescription(desc);
        task.setTargetDate(date);
        task.setCompleted(task.isCompleted());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findByUserAndCompletedIsFalse(User user) {
        return taskRepository.findByUserAndCompletedIsFalse(user);
    }

    @Override
    public List<Task> findByUserAndCompletedIsTrue(User user) {
        return taskRepository.findByUserAndCompletedIsTrue(user);
    }

    @Override
    public Task uncompleteTaskById(Long id) {
        Task task = taskRepository.findById(id);
        task.setCompleted(false);
        return taskRepository.save(task);
    }
}
