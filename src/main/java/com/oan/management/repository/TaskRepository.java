package com.oan.management.repository;

import com.oan.management.model.Task;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);
    List<Task> findByUserAndCompletedIsTrue(User user);
    List<Task> findByUserAndCompletedIsFalse(User user);
    List<Task> findByUserAndCompletedIsFalseAndApprovedIsTrue(User user);
    List<Task> findByUserAndCompletedIsTrueAndApprovedIsTrue(User user);
    List<Task> findByUserAndApprovedIsFalse(User user);
    Task findById(Long id);
    Task getById(Long id);
    void deleteById(Long id);
}
