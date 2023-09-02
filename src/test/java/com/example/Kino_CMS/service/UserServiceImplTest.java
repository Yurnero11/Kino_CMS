package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.UserServiceImpl;
import jakarta.persistence.Id;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    public Logger log;

    @Test
    public void testGetAllUsers_Success() {
        MockitoAnnotations.openMocks(this); // Инициализация моков

        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        Iterable<User> result = userService.getAllUsers();

        assertEquals(userList, result);
    }

    @Test
    public void testGetAllUsers_Exception() {
        MockitoAnnotations.openMocks(this);

        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.getAllUsers());
    }

    @Test
    public void testSearchUsers_Success() {
        MockitoAnnotations.openMocks(this);

        String query = "user";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        User user1 = new User();
        User user2 = new User();
        List<User> userList = List.of(user1, user2);

        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(userRepository.findByUsernameContainingIgnoreCase(query, pageable)).thenReturn(userPage);

        Page<User> result = userService.searchUsers(query, pageable);

        assertEquals(userPage, result);
    }

    @Test
    public void testSearchUsers_Exception() {
        MockitoAnnotations.openMocks(this);

        String query = "user";
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(userRepository.findByUsernameContainingIgnoreCase(query, pageable))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.searchUsers(query, pageable));
    }

    @Test
    public void testSaveUser_Success() {
        MockitoAnnotations.openMocks(this);

        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    public void testSaveUser_Exception() {
        MockitoAnnotations.openMocks(this);

        User user = new User();

        when(userRepository.save(user)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.saveUser(user));
    }

    @Test
    public void testGetAllUsersWithPagination_Success() {
        MockitoAnnotations.openMocks(this);

        Pageable pageable = Pageable.ofSize(10).withPage(0);

        User user1 = new User();
        User user2 = new User();
        List<User> userList = List.of(user1, user2);

        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = userService.getAllUsers(pageable);

        assertEquals(userPage, result);
    }

    @Test
    public void testGetAllUsersWithPagination_Exception() {
        MockitoAnnotations.openMocks(this);

        Pageable pageable = Pageable.ofSize(10).withPage(0);

        when(userRepository.findAll(pageable)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.getAllUsers(pageable));
    }

    @Test
    public void testCountTotalUsers() {
        // Установите ожидаемый результат от userRepository.count()
        long expectedCount = 10;
        when(userRepository.count()).thenReturn(expectedCount);

        // Вызовите метод и проверьте результат
        long actualCount = userService.countTotalUsers();
        assertEquals(expectedCount, actualCount);

        // Проверьте, что метод userRepository.count() был вызван ровно один раз
        verify(userRepository, times(1)).count();
    }

    @Test
    public void testFindAllPage() {
        // Создаем тестовые данные
        List<User> users = Arrays.asList(
                new User(),
                new User(),
                new User()
        );

        // Создаем объект Page с тестовыми данными
        Page<User> page = new PageImpl<>(users);

        // Указываем, что при вызове userRepository.findAll с определенными параметрами, метод должен возвращать page
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Вызываем метод findAllPage
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Page<User> result = userService.findAllPage(pageNumber, pageSize);

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(page, result);

        // Проверяем, что метод userRepository.findAll был вызван с нужными параметрами
        verify(userRepository, times(1)).findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Test
    public void testCountTotalUsers1() {
        // Создаем тестовое значение, которое мы ожидаем от userRepository.count()
        long expectedCount = 42L;

        // Указываем, что при вызове userRepository.count(), метод должен возвращать expectedCount
        when(userRepository.count()).thenReturn(expectedCount);

        // Вызываем метод countTotalUsers
        long result = userService.countTotalUsers();

        // Проверяем, что результат соответствует ожиданиям
        assertEquals(expectedCount, result);

        // Проверяем, что метод userRepository.count был вызван один раз
        verify(userRepository, times(1)).count();
    }
}
