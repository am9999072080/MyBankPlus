package com.am.MyBankPlus.controller;

import com.am.MyBankPlus.dto.Register;
import com.am.MyBankPlus.model.User;
import com.am.MyBankPlus.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/reg")
public class RegController {
    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "reg/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") Register register) {
        User user = new User();
        user.setDateOfBirth(register.getDateOfBirth());
        if (userDetailsService.saveUser(register) == null) {
            return "reg/errors-user-add";
        } else if (user.getAge() < 18) {
            return "reg/error-small-reg";
        } else {
            return "reg/user-home";
        }
    }
}
