package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lastbanner")
public class LastBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int banner_id;

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

    @Column(name = "speed")
    private int speed;
}
