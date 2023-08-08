package com.example.Kino_CMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "registration_date")
    private Date registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = new Date();
    }

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;

    @Column(name = "card_number")
    private String cardNumber;


    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "password_repit")
    private String passwordRepit;

    @Column(name = "language")
    private String language;

    @Column(name = "gender")
    private String gender;

}
