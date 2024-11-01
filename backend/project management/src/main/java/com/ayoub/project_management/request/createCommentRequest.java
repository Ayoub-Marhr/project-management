package com.ayoub.project_management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createCommentRequest {
    private Long issueId;
    private String content;

}
