package com.oan.management.repository;

import com.oan.management.model.Task;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Oan on 18/01/2018.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);

    Task findById(Long id);

    Task getById(Long id);

    Long deleteById(Long id);
}
