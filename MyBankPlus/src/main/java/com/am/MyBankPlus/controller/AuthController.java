package com.am.MyBankPlus.controller;

import com.am.MyBankPlus.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final UserServiceImpl userService;


    @GetMapping("/")
    public String getUserAut(Authentication authentication, Model model) {
        if (userService.getUserAut(authentication).getAge() < 18) {
            return "reg/errors-small-user";
        } else {
            model.addAttribute("users", userService.getAll());
            return "reg/main";
        }
    }

    @GetMapping("/aut")
    public String aut(Authentication authentication, Model model) {
        model.addAttribute("me", userService.getUserAut(authentication));
        return "reg/main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "ВХОД В MyBank+");
        model.addAttribute("calculateGiniCoefficient", userService.calculateGiniCoefficient());
        return "reg/login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }
}
