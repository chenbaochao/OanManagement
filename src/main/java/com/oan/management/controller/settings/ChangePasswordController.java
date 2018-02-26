package com.oan.management.controller.settings;

import com.oan.management.model.User;
import com.oan.management.repository.UserRepository;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Oan on 1/02/2018.
 * @author Oan Stultjens
 * Controller for the Change Password page
 */
@Controller
public class ChangePasswordController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/changepassword")
    public String changePasswordPage(Model model, Authentication authentication) {
        User userLogged = userService.findByUser(authentication.getName());
        if (userLogged != null) {
            model.addAttribute("loggedUser", userLogged);
        }
        return "changepassword";
    }

    @PostMapping("changepassword")
    public String updatePassword(Authentication authentication, User user, @RequestParam String comfirmPass) {
        User userLogged = userService.findByUser(authentication.getName());
        if (user.getPassword().length() >= 4 && user.getPassword().length() <= 25) {
            if (user.getPassword().equals(comfirmPass)) {
                userLogged.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(userLogged);
                return "redirect:/changepassword?success";
            } else {
                return "redirect:/changepassword?comfirm";
            }
        } else {
            return "redirect:/changepassword?error";
        }
    }
}
