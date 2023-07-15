package com.example.Kino_CMS.entity;

import org.hibernate.annotations.Cascade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cinemas")
public class Cinemas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinema_id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo_image_path")
    private String logo_image_path;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "description")
    private String description;

    @Column(name = "top_banner_image_path")
    private String top_banner_image_path;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id")
    private Gallary gallery;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private SeoBlocks seoBlocks;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Halls> halls;
}
