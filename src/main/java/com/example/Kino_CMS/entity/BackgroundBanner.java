package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "backgroundbanner")
public class BackgroundBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int banner_id;

    @Column(name = "image_path")
    private String image_path;

    @Column(name = "background_type")
    private String background_type;
}
