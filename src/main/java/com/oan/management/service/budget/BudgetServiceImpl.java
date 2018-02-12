package com.oan.management.service.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.User;
import com.oan.management.repository.budget.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 9/02/2018.
 */

@Service
public class BudgetServiceImpl implements BudgetService{
    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    ExpenseService expenseService;

    @Override
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @Override
    public List<Budget> findAllByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }

    @Override
    public Budget save(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget findById(Long id) {
        return budgetRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        budgetRepository.delete(budgetRepository.findById(id));
    }
}
