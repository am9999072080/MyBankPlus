package com.am.MyBankPlus.controller;

import com.am.MyBankPlus.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/without-auth")
public class MainController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/home")
    public String iCan(Model model) {
        model.addAttribute("title", "НАДЁЖНЫЙ БАНК ДЛЯ КАЖДОГО!");
        model.addAttribute("calculateGiniCoefficient", userService.calculateGiniCoefficient());
        return "home";
    }

    @GetMapping("/exchange")
    public String Exchange(Model model) {
        model.addAttribute("title", "_Exchange_");
        return "exchange";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Преимущества MyBank+ карт");
        return "about";
    }
}
