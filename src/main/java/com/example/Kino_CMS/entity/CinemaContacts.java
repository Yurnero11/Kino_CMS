package com.example.Kino_CMS.entity;

import com.example.Kino_CMS.entity.SeoBlocks;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cinemascontacts")
public class CinemaContacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinema_id;

    @Column(name = "cinema_name")
    private String cinema_name;

    @Column(name = "address")
    private String address;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "logo_path")
    private String logo_path;

    @Column(name = "creation_date")
    private Date creation_date;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
        creation_date = new Date();
    }
}
