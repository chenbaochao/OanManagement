package com.oan.management.controller.social;

import com.oan.management.model.Image;
import com.oan.management.model.Message;
import com.oan.management.model.Rank;
import com.oan.management.model.User;
import com.oan.management.service.image.ImageService;
import com.oan.management.service.message.MessageService;
import com.oan.management.service.rank.RankService;
import com.oan.management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Oan Stultjens
 * Controller for the Profile page
 */

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RankService rankService;

    @GetMapping("/profile")
    public String root(HttpServletRequest req, Model model, Authentication authentication, @RequestParam(required = false) Long id) {
        User userLogged = userService.findByUser(authentication.getName());
        List<Message> unreadMessages = messageService.findByReceiverAndOpenedIs(userLogged, 0);

        model.addAttribute("loggedUser", userLogged);
        req.getSession().setAttribute("unreadMessages", unreadMessages.size());

        if (id != null) {
            User paramUser = userService.findById(id);
            if (paramUser != null) {
                model.addAttribute("paramUser", paramUser);
                // Get user avatar
                Image avatar = imageService.getUserImage(paramUser);
                model.addAttribute("avatar", "/img/"+avatar.getUrl());
                // Get user rank
                Rank userRank = rankService.getUserRank(paramUser);
                model.addAttribute("paramUserRank", userRank);
            } else {
                return "redirect:/profile?id="+userLogged.getId();
            }
        } else {
            return "redirect:/profile?id="+userLogged.getId();
        }
        return "profile";
    }
}
