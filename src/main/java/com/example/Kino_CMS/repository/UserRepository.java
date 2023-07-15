package com.example.Kino_CMS.repository;


import com.example.Kino_CMS.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // Метод поиска пользователей по имени пользователя
    List<User> findByUsernameContaining(String username);
    long countByGender(String gender);
    List<User> findByEmailIn(List<String> emails);
}