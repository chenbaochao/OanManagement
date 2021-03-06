package com.oan.management.service.bug;

import com.oan.management.model.Bug;
import com.oan.management.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oan on 29/01/2018.
 * @author Oan Stultjens
 * Implementation of {@link BugService}
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

    @Override
    public List<Bug> findByFixedIsFalse() {
        return bugRepository.findByFixedIsFalse();
    }

    @Override
    public Bug findById(Long id) {
        return bugRepository.findOne(id);
    }

    @Override
    public List<Bug> findByFixedIsTrue() {
        return bugRepository.findByFixedIsTrue();
    }
}
