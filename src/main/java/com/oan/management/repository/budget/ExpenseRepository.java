package com.oan.management.repository.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 12/02/2018.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense findById(Long id);
    List<Expense> findAllByBudget(Budget budget);
}
