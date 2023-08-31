package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hall_id;

    @Column(name = "number")
    private int number;

    @Column(name = "description")
    private String description;

    @Column(name = "schema_image_path")
    private String schema_image_path;

    @Column(name = "top_banner_image_path")
    private String top_banner_image_path;

    @Column(name = "creation_date")
    private Date creation_date;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gallery_id")
    private Gallary gallery;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private SeoBlock seoBlock;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
