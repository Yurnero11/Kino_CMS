package com.example.Kino_CMS.repository;


import com.example.Kino_CMS.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    // Метод поиска пользователей по имени пользователя
    List<User> findByUsernameContaining(String username);
    User findByEmail(String email);
    long countByGender(String gender);
    List<User> findByEmailIn(List<String> emails);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    User findByUsername(String username);

    @Transactional
    @Override
    <S extends User> S save(S entity);
    Page<User> findAll(Pageable pageable);

}