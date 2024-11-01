package com.ayoub.project_management.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags; // Added field for tags
}
