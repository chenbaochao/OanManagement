package com.oan.management.service;

import com.oan.management.model.Bug;
import com.oan.management.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 29/01/2018.
 */

@Service
public class BugServiceImpl implements BugService {
    @Autowired
    public BugRepository bugRepository;

    @Override
    public List<Bug> findAll() {
        return bugRepository.findAll();
    }

    @Override
    public Bug save(Bug bug) {
        return bugRepository.save(bug);
    }
}
