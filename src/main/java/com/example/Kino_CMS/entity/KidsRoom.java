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
@Table(name = "kidsroom")
public class KidsRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kids_room_id;

    @Column(name = "kidsroom_name")
    private String kidsroom_name;

    @Column(name = "description")
    private String description;

    @Column(name = "main_image_path")
    private String main_image_path;

    @Column(name = "image_path_1")
    private String image_path_1;

    @Column(name = "image_path_2")
    private String image_path_2;

    @Column(name = "image_path_3")
    private String image_path_3;

    @Column(name = "image_path_4")
    private String image_path_4;

    @Column(name = "image_path_5")
    private String image_path_5;

    @Column(name = "seo_url")
    private String seo_url;

    @Column(name = "seo_title")
    private String seo_title;

    @Column(name = "seo_keywords")
    private String seo_keywords;

    @Column(name = "seo_description")
    private String seo_description;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private Date creation_date;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }
}
