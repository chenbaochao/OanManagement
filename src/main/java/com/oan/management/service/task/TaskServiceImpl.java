package com.oan.management.service.task;

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

    /**
     * Returns a String of a motivational text, according to the amount of tasks to complete for the User
     * @param taskList List of Task's
     * @param user {@link User}
     * @return String of motivation text
     */
    @Override
    public String getMotivationalMessage(List<Task> taskList, User user) {

        if (taskList.size() == 0 && taskRepository.findByUserAndCompletedIsTrue(user).size() == 0) {
           return "Hmm... What about giving yourself some tasks?";
        } else if (taskList.size() == 0 && taskRepository.findByUserAndCompletedIsTrue(user).size() > 0) {
            return "Good job! You've completed all your tasks! Be proud about yourself.";
        } else if (taskList.size() == 1) {
            return "What, only 1 task? You should finish your work!";
        } else if (taskList.size() > 1 && taskList.size() <= 5){
            return "You only have "+taskList.size()+" tasks... That's not that much. Go complete them!";
        } else if (taskList.size() > 5 && taskList.size() <= 10){
            return "You've got some work there, "+taskList.size()+" tasks to complete. Try to complete as much as possible today.";
        } else if (taskList.size() > 10 ){
            return taskList.size()+" tasks! Let's see how many you could complete today! Proof yourself!";
        } else {
            return "Error";
        }
    }

    @Override
    public Task uncompleteTaskById(Long id) {
        Task task = taskRepository.findById(id);
        task.setCompleted(false);
        return taskRepository.save(task);
    }
}
