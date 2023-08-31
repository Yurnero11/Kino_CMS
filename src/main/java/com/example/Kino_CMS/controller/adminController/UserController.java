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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
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
                                  Model model,
                                  Authentication authentication) {

            int pageSize = 6;
            Page<User> userPage = userServiceImpl.findAllPage(page, pageSize);
            List<User> users = userPage.getContent();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String currentUserEmail = userDetails.getUsername();

            User currentUser = userRepository.findByEmail(currentUserEmail); // Здесь предполагается, что у вас есть метод для поиска пользователя по email

            model.addAttribute("currentUser", currentUser);

            model.addAttribute("users", users);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", userPage.getTotalPages());

            int nextPage = page + 1;
            int totalPages = userPage.getTotalPages();
            model.addAttribute("nextPage", nextPage);
            model.addAttribute("totalPages", totalPages);

            long totalUsers = userServiceImpl.countTotalUsers();
            boolean showPagination = totalUsers > 6;
            model.addAttribute("showPagination", showPagination);

            return "admin/customers/list";
        }

        @PostMapping("/admin/users")
        public String nextPageUsers(@RequestParam("page") int page) {
            return "redirect:/admin/users?page=" + page;
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
                               @RequestParam(value = "gender", required = false) String gender,
                               Model model){
            User user = userRepository.findById(id).orElseThrow();
            user.setUsername(username);
            user.setEmail(email);
            user.setBirthday(birthday);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCity(city);
            user.setAddress(address);
            if (gender == null || !gender.equals(user.getGender())) {
                user.setGender(gender);
            }
            userRepository.save(user);
            return "redirect:/admin/users";
        }

        @PostMapping("/admin/user/{id}/remove")
        @PreAuthorize("hasRole('ADMIN')")
        public String removeUser(@PathVariable(value = "id") long id, Model model, Principal principal) {
            // Получение имени текущего пользователя
            String currentUsername = principal.getName();

            // Получение текущего пользователя по имени
            User currentUser = userRepository.findByUsername(currentUsername);

            // Получение пользователя, которого вы пытаетесь удалить
            Optional<User> userToDelete = userRepository.findById(id);

            // Проверка, не пытается ли текущий пользователь удалить самого себя
            if (userToDelete.isPresent() && userToDelete.get().equals(currentUser)) {
                // Возвращайте ошибку или выполните другие действия по вашему усмотрению
                // Например, перенаправьте пользователя на страницу с сообщением об ошибке
                return "redirect:/error";
            }

            // Если текущий пользователь не пытается удалить себя, выполните удаление
            userToDelete.ifPresent(user -> userRepository.delete(user));

            // Перенаправление на страницу со списком пользователей
            return "redirect:/admin/users";
        }
    }
