package com.oan.management.controller.task;

import com.oan.management.model.Message;
import com.oan.management.model.Task;
import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.service.message.MessageService;
import com.oan.management.service.task.TaskService;
import com.oan.management.service.user.RankService;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Controller
public class TaskController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RankService rankService;

    public User getLoggedUser(Authentication authentication) {
        return userService.findByUser(authentication.getName());
    }

    @GetMapping("/task-list")
    public String tasklist(HttpServletRequest req, Model model, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        model.addAttribute("task", new Task());
        // Get uncompleted tasks and sort by date
        List<Task> taskList = taskService.findByUserAndCompletedIsFalse(userLogged);
        taskList.sort(Comparator.comparing(Task::getTargetDate));
        // get completed tasks and sort by date
        List<Task> completedTasksList = taskService.findByUserAndCompletedIsTrue(userLogged);
        completedTasksList.sort(Comparator.comparing(Task::getTargetDate));
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
            model.addAttribute("completedTasks", completedTasksList);
            req.getSession().setAttribute("tasksLeftSession", taskList.size());
            req.getSession().setAttribute("unreadMessages", unreadMessages.size());
        }
        String  today = new Date(Calendar.getInstance().getTime().getTime()).toString();
        model.addAttribute("today", today);
        System.out.println(taskService.getOne(8L).getTargetDate().toString());
        return "task-list";
    }

    @PostMapping("/task-list")
    public String newTask(Model model, @Valid Task task, @RequestParam("targetDate") String date, Authentication authentication) {
        // Get logged in user and his tasks
        User userLogged = getLoggedUser(authentication);
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
        taskService.save(new Task(userLogged, task.getDescription(), task.getTargetDate(), task.isCompleted() ));
        userLogged.setTasksMade(userLogged.getTasksMade()+1);
        userRepository.save(userLogged);
        return "redirect:/task-list";
    }

    @GetMapping("/task-delete")
    public String deleteTask(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        // Check if it's user's task
        if (taskService.findByUser(getLoggedUser(authentication)).contains(taskService.getOne(id))) {
            taskService.deleteTaskById(id);
            return "redirect:/task-list?deleted";
        } else {
            return "redirect:/task-list?notfound";
        }
    }

    @GetMapping("/task-edit")
    public String editTaskOnScreen(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Task> taskList = taskService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
        }
        // Check if it's user's task
        if (taskService.findByUser(getLoggedUser(authentication)).contains(taskService.getOne(id))) {
            model.addAttribute("task", taskService.getOne(id));
            return "/task-edit";
        } else {
            return "redirect:task-list?notfound";
        }
    }

    @PostMapping("/task-edit")
    public String editTask(Model model, Task task, Authentication authentication) {
        User userLogged = getLoggedUser(authentication);
        List<Task> taskList = taskService.findByUser(userLogged);
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("tasks", taskList);
        }
        taskService.editById(task.getId(),task.getDescription(),task.getTargetDate(), task.isCompleted());
        return "redirect:task-list?updated";
    }

    @GetMapping("/task-complete")
    public String completeTask(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        taskService.completeTaskById(id);
        User userLogged = getLoggedUser(authentication);
        // Update statistics
        userLogged.setTasksCompleted(userLogged.getTasksCompleted()+1);
        // Update rank
        if (rankService.findByUser(userLogged)==null) {
            rankService.setRank(userLogged, "Newbie", 1);
        } else {
            rankService.checkRank(userLogged);
        }
        // Save user
        userRepository.save(userLogged);
        return "redirect:/task-list";
    }

    @GetMapping("/task-uncomplete")
    public String uncompleteTask(Model model, Task task, @RequestParam Long id, Authentication authentication) {
        taskService.uncompleteTaskById(id);
        User userLogged = getLoggedUser(authentication);
        // Update statistics
        userLogged.setTasksCompleted(userLogged.getTasksCompleted()-1);
        userRepository.save(userLogged);
        return "redirect:/task-list";
    }
}
