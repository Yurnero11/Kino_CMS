package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "about_movie")
public class AboutMovie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "composer")
    private String composer;

    @Column(name = "producer")
    private String producer;

    @Column(name = "director")
    private String director;

    @Column(name = "screenwriter")
    private String screenwriter;

    @Column(name = "cinematographer")
    private String cinematographer;

    @Column(name = "genre")
    private String genre;

    @Column(name = "budget")
    private BigDecimal budget;

    @Column(name = "age_rating")
    private String age_rating;

    @Column(name = "duration")
    private Time duration;

    @OneToOne(mappedBy = "aboutMovie")
    private Movie movie;

    // Конструктори, геттери, сеттери та інше
}