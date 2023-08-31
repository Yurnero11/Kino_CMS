package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movie_id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "main_image_path")
    private String main_page_path;

    @Column(name = "trailer_url")
    private String trailer_url;

    @Column(name = "movie_type")
    private String movieType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id")
    private Gallary gallery;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private SeoBlock seoBlock;

    @Column(name = "movie_data")
    private String movie_data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "about_movie_id")
    private AboutMovie aboutMovie;
}
