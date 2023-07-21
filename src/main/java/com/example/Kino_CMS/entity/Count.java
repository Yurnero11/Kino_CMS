package com.example.Kino_CMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "count_email")
public class Count {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "count")
    private int count;

    public Count(int sentCount) {
        this.count = sentCount;
    }

    public Count() {}

    public void setCount(int sentCount) {
        this.count = sentCount;
    }
}
