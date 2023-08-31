package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mainbanners")
public class MainBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bannerId;

    @Column(name = "image_path_1")
    private String imagePath1;

    @Column(name = "image_path_2")
    private String imagePath2;

    @Column(name = "image_path_3")
    private String imagePath3;

    @Column(name = "image_path_4")
    private String imagePath4;

    @Column(name = "image_path_5")
    private String imagePath5;

    @Column(name = "url1")
    private String url1;

    @Column(name = "url2")
    private String url2;

    @Column(name = "url3")
    private String url3;

    @Column(name = "url4")
    private String url4;

    @Column(name = "url5")
    private String url5;

    @Column(name = "text1")
    private String text1;

    @Column(name = "text2")
    private String text2;

    @Column(name = "text3")
    private String text3;

    @Column(name = "text4")
    private String text4;

    @Column(name = "text5")
    private String text5;

    @Column(name = "speed")
    private int speed;

    @Column(name = "status")
    private String status;
}
