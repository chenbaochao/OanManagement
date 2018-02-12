package com.oan.management.repository;

import com.oan.management.model.Budget;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 9/02/2018.
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAll();
    List<Budget> findAllByUser(User user);
    Budget findById(Long id);
}
