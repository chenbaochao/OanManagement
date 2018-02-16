package com.oan.management;

import com.oan.management.model.User;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oan on 16/02/2018.
 */
@RestController
public class JsonUsersController {
    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public List<User> getUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    @GetMapping("/api/users/usernames")
    public List<String> getUsernames() {
        List<User> users = userService.findAll();
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }
}
