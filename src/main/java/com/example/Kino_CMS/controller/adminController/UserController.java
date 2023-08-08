package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final int pageSize = 6; // Изменили значение pageSize на 6

    @Autowired
    private UserRepository userRepository;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "query", required = false) String query,
                              Model model) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<User> userPage;
        if (query != null && !query.isEmpty()) {
            userPage = userServiceImpl.searchUsers(query, pageable);
        } else {
            userPage = userServiceImpl.getAllUsers(pageable);
        }

        int nextPage = page + 1;
        model.addAttribute("nextPage", nextPage);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());


        return "admin/customers/list";
    }

    @GetMapping("/admin/user/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@PathVariable(value = "id") long id, Model model){
        if (!userRepository.existsById(id)){
            return "redirect:/admin/users";
        }

        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("users", res);
        return "admin/customers/user-edit";
    }

    @PostMapping("/admin/user/{id}/edit")
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
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/user/{id}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUser(@PathVariable(value = "id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/search")
    public String searchUsers(@RequestParam(value = "query", required = false) String query, Model model) {
        List<User> users;

        if (query != null && !query.isEmpty()) {
            users = userRepository.findByUsernameContaining(query);
        } else {
            users = new ArrayList<>(); // Создаем пустой список пользователей
        }

        model.addAttribute("users", users);

        return "admin/customers/list";
    }
}
