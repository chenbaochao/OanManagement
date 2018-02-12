package com.oan.management.service.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.Expense;
import com.oan.management.repository.budget.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 12/02/2018.
 */

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public Expense findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<Expense> findAllByBudget(Budget budget) {
        return expenseRepository.findAllByBudget(budget);
    }

    @Override
    public Double getTotalIncome(List<Expense> expenseList) {
        Double total = 0.0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        return total;
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.delete(expenseRepository.findById(id));
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }
}
