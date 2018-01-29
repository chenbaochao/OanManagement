package com.oan.management.repository;

import com.oan.management.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 29/01/2018.
 */
public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findAll();
}
