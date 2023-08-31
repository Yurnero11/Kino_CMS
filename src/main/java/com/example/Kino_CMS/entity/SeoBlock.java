package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seoblocks")
public class SeoBlock {
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

    @OneToOne(mappedBy = "seoBlock")
    private Movie movie;

    @OneToOne(mappedBy = "seoBlock")
    private Cinema cinema;

    @OneToOne(mappedBy = "seoBlock")
    private Hall hall;

    @OneToOne(mappedBy = "seoBlock")
    private News news;

    @OneToOne(mappedBy = "seoBlock")
    private Promotion promotion;
}
