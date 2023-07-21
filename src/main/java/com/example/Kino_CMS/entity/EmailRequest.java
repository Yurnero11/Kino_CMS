package com.example.Kino_CMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String email;
    private boolean sendToAll;
    private String subject = "Admin";
    private String content = "Admin";
}
