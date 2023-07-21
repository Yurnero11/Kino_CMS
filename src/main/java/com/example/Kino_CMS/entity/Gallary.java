package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gallery")
public class Gallary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id")
    private int galleryId;

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

    @OneToOne(mappedBy = "gallery")
    private Movies movies;

    @OneToOne(mappedBy = "gallery")
    private Cinemas cinemas;

    @OneToOne(mappedBy = "gallery")
    private Halls halls;

    @OneToOne(mappedBy = "gallery")
    private News news;

    @OneToOne(mappedBy = "gallery")
    private Promotions promotions;

}
