package com.oan.management.repository;

import com.oan.management.model.Rank;
import com.oan.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Oan on 19/02/2018.
 */
public interface RankRepository extends JpaRepository<Rank, Long> {
    Rank findByUser(User user);
}
