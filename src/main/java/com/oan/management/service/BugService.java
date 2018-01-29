package com.oan.management.service;

import com.oan.management.model.Bug;

import java.util.List;

/**
 * Created by Oan on 29/01/2018.
 */
public interface BugService {
    List<Bug> findAll();
    Bug save(Bug bug);
}
