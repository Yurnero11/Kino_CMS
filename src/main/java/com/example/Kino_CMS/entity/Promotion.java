package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promotion_id;

    @Column(name = "promotion_title")
    private String promotion_title;

    @Column(name = "creation_date")
    private Date creation_date;

    @Column(name = "publication_date")
    private LocalDate publication_date;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }

    @Column(name = "status")
    private String status;

    @Column(name = "promotion_description")
    private String promotion_description;

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
