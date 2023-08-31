package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String register(){
        return "public/login-page/registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                               Model model) {

        // Проверка на уникальность имени пользователя
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "public/login-page/registration";
        }

        User existingEmailUser = userRepository.findByEmail(email);
        if (existingEmailUser != null) {
            model.addAttribute("error", "Пользователь с таким email уже существует");
            return "public/login-page/registration";
        }

        // Проверка на пустые пароли
        if (password == null || confirmPassword == null || password.isEmpty() || confirmPassword.isEmpty()) {
            model.addAttribute("error", "Password cannot be empty.");
            return "public/login-page/registration";
        }

        // Проверка, что пароли совпадают
        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error";
        }

        // Создание нового объекта User
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setBirthday(birthday);

        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        newUser.setPasswordHash(hashedPassword);
        String hashedConfirmPassword = new BCryptPasswordEncoder().encode(confirmPassword);
        newUser.setPasswordRepit(hashedConfirmPassword); // Используем один и тот же хеш для обоих полей

        userService.saveUser(newUser);

        return "redirect:/login";
    }

}
