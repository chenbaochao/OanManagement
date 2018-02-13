package com.oan.management.service.bug;

import com.oan.management.model.Bug;

import java.util.List;

/**
 * Created by Oan on 29/01/2018.
 */
public interface BugService {
    List<Bug> findAll();
    Bug save(Bug bug);
    List<Bug> findByFixedIsFalse();
    Bug findById(Long id);
    List<Bug> findByFixedIsTrue();
}
