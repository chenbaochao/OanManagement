package com.oan.management.service.user;

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
    void addBugReport(User userLogged);
    User editByUser(User user, String firstName, String lastName, String country, int age, String facebook, String skype,
                    String github, String email, String username);
}
