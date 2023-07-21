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
@Table(name = "mainpage")
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int main_page_id;

    @Column(name = "creation_date")
    private Date creation_date;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }

    @Column(name = "seo_text")
    private String seo_text;

    @Column(name = "status")
    private String status;

    @Column(name = "phone_number_1")
    private String phone_number_1;

    @Column(name = "phone_number_2")
    private String phone_number_2;

    @Column(name = "seo_url")
    private String seo_url;

    @Column(name = "seo_title")
    private String seo_title;

    @Column(name = "seo_keywords")
    private String seo_keywords;

    @Column(name = "seo_description")
    private String seo_description;
}
