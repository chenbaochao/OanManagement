package com.oan.management.service;

import com.oan.management.model.User;
import com.oan.management.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User save(UserRegistrationDto registration);
    User findByUser(String user);
    User findById(Long id);
    List<User> findAll();
}
