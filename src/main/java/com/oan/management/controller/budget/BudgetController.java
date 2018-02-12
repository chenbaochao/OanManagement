package com.oan.management.controller.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.User;
import com.oan.management.service.BudgetService;
import com.oan.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Oan on 9/02/2018.
 */

@Controller
public class BudgetController {
    @Autowired
    UserService userService;

    @Autowired
    BudgetService budgetService;

    @GetMapping("/budget-list")
    public String getBudgetManager(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Budget> budgetList = budgetService.findAllByUser(userLogged);
        budgetList.sort(Comparator.comparing(Budget::getId).reversed());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            model.addAttribute("budgetList", budgetList);
        }
        return "budget-list";
    }

    @GetMapping("/budget-new")
    public String newBudget(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        model.addAttribute("budget", new Budget());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "budget-new";
    }

    @PostMapping("/budget-new")
    public String saveNewBudget(Model model, Budget budget, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        if (budget.getTitle().length() >= 3 ) {
            if (budget.getBudgetAmount() >= 0.01) {
                Budget userBudget = new Budget(budget.getTitle(), budget.getBudgetAmount(), userLogged);
                budgetService.save(userBudget);
                return "redirect:/budget-list?success";
            } else {
                return "redirect:/budget-new?error";
            }
        } else {
            return "redirect:/budget-new?error";
        }
    }

    @GetMapping("/budget")
    public String showBudget(Model model, Authentication authentication, @RequestParam(required = false) Long id) {
        User userLogged = userService.findByUser(authentication.getName());

        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
            if (id != null) {
                Budget paramBudget = budgetService.findById(id);
                model.addAttribute("paramBudget", paramBudget);
            } else {
                return "budget-list?notfound";
            }
        }
        return "budget";
    }

}
