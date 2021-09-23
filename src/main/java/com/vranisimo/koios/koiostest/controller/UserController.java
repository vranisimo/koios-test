package com.vranisimo.koios.koiostest.controller;

import com.vranisimo.koios.koiostest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/adduser")
    public String showAddUserForm(@Valid User user, Model model) {
        // TODO add default date
        //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        //        model.addAttribute("today", formatter.format(LocalDate.now()));
        return "addUser";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addUser";
        }

        Long res = userRepository.findByEmail(user.getEmail());
        if (res != null && res > 0) {
            FieldError error = new FieldError("user", "email", "Email already exist.");
            result.addError(error);
            return "addUser";
        }

        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String showEditForm(@Param("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));

        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "editUser";
        }

        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteUser(@Param("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        userRepository.delete(user);
        return "redirect:/";
    }
}