package com.ayoub.project_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    private User user;

    @ManyToOne
    private Issue issue;}
