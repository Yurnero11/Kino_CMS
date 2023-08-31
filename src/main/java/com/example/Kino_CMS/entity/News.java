package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private int news_id;

    @Column(name = "news_title")
    private String news_title;

    @Column(name = "creation_date")
    private Date creation_date;

    @Column(name = "date_publication")
    private LocalDate date_publication;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }

    @Column(name = "status")
    private String status;

    @Column(name = "news_description")
    private String news_description;

    @Column(name = "main_image_path")
    private String main_image_path;

    @Column(name = "video_url")
    private String video_url;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id")
    private Gallary gallery;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private SeoBlock seoBlock;
}
