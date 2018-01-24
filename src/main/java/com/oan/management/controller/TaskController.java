package com.oan.management.controller;

import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.repository.TaskRepository;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class TaskController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskService;

    @GetMapping("/task-list")
    public String tasklist(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        model.addAttribute("task", new Task());
        List<Task> taskList = taskService.findByUser(userLogged);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
            req.getSession().setAttribute("tasksLeftSession", taskList.size());
        }
        return "task-list";
    }

    @PostMapping("/task-list")
    public String newTask(Model model, @Valid Task task, BindingResult result, @RequestParam("targetDate") String date, Authentication authentication) {
        // Get logged in user and his tasks
        User userLogged = userService.findByEmail(authentication.getName());
        List<Task> taskList = taskService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
        }

        // HTML String date to Date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.sql.Date sqlDate = new java.sql.Date(format.parse(date).getTime());
            task.setTargetDate(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (result.hasErrors() && task.getTargetDate()==null) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.toString());
                result.rejectValue("taskname", "Error");
            }
            return "task-list";
        }

        taskService.save(new Task(userLogged, task.getDescription(), task.getTargetDate(), false ));
        return "redirect:/task-list";
    }

    @GetMapping("/task-delete")
    public String deleteTask(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        taskService.delete(id);
        return "redirect:/task-list";
    }

    @GetMapping("/task-edit")
    public String editTaskOnScreen(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Task> taskList = taskService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
        }

        model.addAttribute("task", taskService.getOne(id));
        return "/task-edit";
    }

    @PostMapping("/task-edit")
    public String editTask(Model model, Task task, Authentication authentication) {
        User userLogged = userService.findByEmail(authentication.getName());
        List<Task> taskList = taskService.findByUser(userLogged);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
        }

        Long previous_id = task.getId();
        taskService.save(new Task(userLogged, task.getDescription(), task.getTargetDate(), task.isCompleted()));
        taskService.delete(taskService.findById(previous_id));

        return "redirect:task-list";
    }

}
