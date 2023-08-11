package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import com.example.Kino_CMS.service.impl.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class UserProfileController {
    private final UserRepository userRepository;
    private final SessionServiceImpl sessionServiceImpl;

    @Autowired
    public UserProfileController(UserRepository userRepository, SessionServiceImpl sessionServiceImpl) {
        this.userRepository = userRepository;
        this.sessionServiceImpl = sessionServiceImpl;
    }

    @GetMapping("/user/profile/{id}")
    public String userEdit(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            model.addAttribute("error", "Пользователь с таким id не найден");
            return "public/user/user-edit";
        }
        model.addAttribute("birthday", user.getBirthday());


        model.addAttribute("user", user);
        model.addAttribute("id", id); // Добавляем id пользователя в модель


        return "public/user/user-edit";
    }


    @PostMapping("/user/profile/{id}")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @PathVariable long id,
                                @RequestParam(value = "prevBirthday", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate prevBirthday,
                                @RequestParam(value = "language", required = false) String language,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "originalPasswordHash") String originalPasswordHash,
                                @RequestParam(value = "originalPasswordRepit") String originalPasswordRepit,
                                RedirectAttributes redirectAttributes,

                                Model model) {

        // Проверяем, существует ли пользователь с таким id в базе данных
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким id не найден");
            return "public/user/user-edit";
        }

        User existingUser = existingUserOptional.get();

        // If password fields are empty, retain the original password hashes
        if (updatedUser.getPasswordHash().isEmpty() && updatedUser.getPasswordRepit().isEmpty()) {
            existingUser.setPasswordHash(originalPasswordHash);
            existingUser.setPasswordRepit(originalPasswordRepit);
        } else if (!updatedUser.getPasswordHash().equals(updatedUser.getPasswordRepit())) {
            // If passwords are not empty and do not match, show an error message
            model.addAttribute("error", "Пароли не совпадают");
            return "public/user/user-edit";
        } else {
            // If passwords are not empty and match, update the password hashes
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(updatedUser.getPasswordHash());
            existingUser.setPasswordHash(hashedPassword);

            PasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
            String hashedPassword1 = passwordEncoder1.encode(updatedUser.getPasswordRepit());
            existingUser.setPasswordRepit(hashedPassword1);
        }

        // Обновляем остальные поля пользователя, только если они изменились
        if (!existingUser.getFirstName().equals(updatedUser.getFirstName())) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (!existingUser.getLastName().equals(updatedUser.getLastName())) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (existingUser.getEmail() == null || !existingUser.getEmail().equals(updatedUser.getEmail())) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getBirthday() != null) {
            existingUser.setBirthday(updatedUser.getBirthday());
        } else {
            // If the birthday is not provided in the form data, use the previous value
            existingUser.setBirthday(prevBirthday);
        }

        if (existingUser.getCity() == null || !existingUser.getCity().equals(updatedUser.getCity())) {
            existingUser.setCity(updatedUser.getCity());
        }
        if (existingUser.getPhone() == null || !existingUser.getPhone().equals(updatedUser.getPhone())) {
            existingUser.setPhone(updatedUser.getPhone());
        }
        if (existingUser.getAddress() == null ||!existingUser.getAddress().equals(updatedUser.getAddress())) {
            existingUser.setAddress(updatedUser.getAddress());
        }
        if (existingUser.getCardNumber() == null || !existingUser.getCardNumber().equals(updatedUser.getCardNumber())) {
            existingUser.setCardNumber(updatedUser.getCardNumber());
        }



        if ("ukrainian".equals(language) || "nonUkrainian".equals(language)) {
            existingUser.setLanguage(language);
        }

        // Update the gender if it's provided in the form data
        if ("male".equals(gender) || "female".equals(gender)) {
            existingUser.setGender(gender);
        }
        // Добавьте проверки и для других полей

        // Сохраняем обновленные данные пользователя в базе данных
        userRepository.save(existingUser);
        // Сохраняем userId в сессии после успешного обновления профиля
        sessionServiceImpl.setUserIdInSession();
        redirectAttributes.addFlashAttribute("updateSuccess", true);

        return "redirect:/user/profile/{id}"; // Перенаправление на страницу профиля с обновленными данными
    }

}
