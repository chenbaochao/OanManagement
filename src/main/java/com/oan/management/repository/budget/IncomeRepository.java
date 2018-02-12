package com.oan.management.repository.budget;

import com.oan.management.model.Budget;
import com.oan.management.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 12/02/2018.
 */
public interface IncomeRepository extends JpaRepository<Income, Long> {
    Income findById(Long id);
    List<Income> findAllByBudget(Budget budget);
}
