package com.oan.management.repository;

import com.oan.management.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Oan on 25/01/2018.
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
