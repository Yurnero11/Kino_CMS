package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/customers/list";
    }

    @GetMapping("/user/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@PathVariable(value = "id") long id, Model model){
        if (!userRepository.existsById(id)){
            return "redirect:/users";
        }

        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("users", res);
        return "/customers/user-edit";
    }

    @PostMapping("/user/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@PathVariable(value = "id") long id,
                           @RequestParam String username,
                           @RequestParam String email,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String city,
                           @RequestParam String address,
                           @RequestParam String gender,
                           Model model){
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(username);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCity(city);
        user.setAddress(address);
        user.setGender(gender);
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/user/{id}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUser(@PathVariable(value = "id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/users";
    }

    @GetMapping("/users/search")
    public String searchUsers(@RequestParam("query") String query, Model model) {
        List<User> users = userRepository.findByUsernameContaining(query);
        model.addAttribute("users", users);
        return "/customers/list";
    }
}
