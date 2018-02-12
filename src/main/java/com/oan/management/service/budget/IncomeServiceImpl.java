package com.oan.management.service.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.Income;
import com.oan.management.repository.budget.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 12/02/2018.
 */

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    IncomeRepository incomeRepository;


    @Override
    public Income findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public List<Income> findAllByBudget(Budget budget) {
        return incomeRepository.findAllByBudget(budget);
    }

    @Override
    public Double getTotalIncome(List<Income> incomeList) {
        Double total = 0.0;
        for (Income income : incomeList) {
            total += income.getAmount();
        }
        return total;
    }
}
