package com.oan.management.service;

import com.oan.management.model.Budget;
import com.oan.management.model.User;
import com.oan.management.repository.BudgetRepository;
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
}
