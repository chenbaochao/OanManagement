package com.oan.management.service.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.Income;

import java.util.List;

/**
 * Created by Oan on 12/02/2018.
 */
public interface IncomeService {
    Income findById(Long id);
    List<Income> findAllByBudget(Budget budget);
    Double getTotalIncome(List<Income> incomeList);
    void deleteById(Long id);
    Income save(Income income);
}
