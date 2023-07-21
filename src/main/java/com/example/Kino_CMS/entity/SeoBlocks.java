package com.example.Kino_CMS.entity;

import com.sun.tools.javac.Main;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seoblocks")
public class SeoBlocks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seo_id")
    private int seoId;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "seoBlocks")
    private Movies movies;

    @OneToOne(mappedBy = "seoBlocks")
    private Cinemas cinemas;

    @OneToOne(mappedBy = "seoBlocks")
    private Halls halls;

    @OneToOne(mappedBy = "seoBlocks")
    private News news;

    @OneToOne(mappedBy = "seoBlocks")
    private Promotions promotions;
}
