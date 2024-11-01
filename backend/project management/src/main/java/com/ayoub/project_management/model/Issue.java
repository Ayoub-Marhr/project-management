package com.ayoub.project_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Changed to camelCase

    private String title;  // Changed to camelCase
    private String description;  // Changed to camelCase
    private String status;  // Changed to camelCase

    @Column(name = "project_id") // Ensure this matches your database schema
    private Long projectId;  // Changed to camelCase

    private String priority;  // Changed to camelCase
    private LocalDate dueDate;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Id_Project")
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}