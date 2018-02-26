package com.oan.management.service.user;

import com.oan.management.model.Role;
import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        user.setRegistrationDate(new Date(Calendar.getInstance().getTime().getTime()));
        return userRepository.save(user);
    }

    @Override
    public User findByUser(String user) {
        return userRepository.findByUsername(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void addBugReport(User userLogged) {
        userLogged.setBugsReported(userLogged.getBugsReported()+1);
        userRepository.save(userLogged);
    }

    @Override
    public User editByUser(User user, String firstName, String lastName, String country, int age, String facebook, String skype, String github, String email, String username) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCountry(country);
        user.setAge(age);
        user.setFacebook(facebook);
        user.setSkype(skype);
        user.setGithub(github);
        user.setEmail(email);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public void incrementMessagesReceivedStats(User user) {
        user.setMessagesReceived(user.getMessagesReceived()+1);
        userRepository.save(user);
    }

    @Override
    public void incrementMessagesSentStats(User user) {
        user.setMessagesSent(user.getMessagesSent()+1);
        userRepository.save(user);
    }

    @Override
    public User setSocialSettings(User user, String facebook, String twitter, String skype, String github) {
        user.setFacebook(facebook);
        user.setTwitter(twitter);
        user.setSkype(skype);
        user.setGithub(github);
        return userRepository.save(user);
    }

    @Override
    public User setName(User user, String firstName, String lastName) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
