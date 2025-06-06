package com.am.MyBankPlus.controller;

import com.am.MyBankPlus.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    @GetMapping("/user/{id}/remove")
    public String getAccount(@PathVariable(value = "id") Long id, Model model) {
        userService.getUserById(id, model);
        return "/";
    }

    @PostMapping("/user/{id}/remove")
    public String deleteAccount(@PathVariable(value = "id") Long id, Model model) {
        userService.userDelete(id, model);
        return "redirect:/";
    }


    @GetMapping("/change-password")
    public String updatePassword() {
        return "reg/change-pass";
    }

    @PostMapping("/change-password")
    public String updatePassword(Authentication authentication, @RequestParam String newPassword, @RequestParam String repeatPassword) {
        if ((userService.updatePassword(authentication, newPassword, repeatPassword)) != null) {
            return "redirect:/";
        } else {
            return "redirect:/logout";
        }
    }
}
