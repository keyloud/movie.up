package com.kosavpa.first.boot.example.controllers;


import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class RegistrationAndLogin_Controller {
    @Autowired
    private UserService userService;

    @ModelAttribute(name = "userEntity")
    public UserEntity addUserEntity(){
        return new UserEntity();
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("UserEntity", new UserEntity());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid UserEntity user, Errors errors, Model model) {
        if (errors.hasErrors() | userService.findByUserName(user.getUsername()) != null){
            model.addAttribute("registrationError", "Пользователь с таким именем уже существует или введённые данные не корректны!");

            return "registration";
        }
        userService.saveUser(user);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
