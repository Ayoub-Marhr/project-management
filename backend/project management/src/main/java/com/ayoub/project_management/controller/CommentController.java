package com.ayoub.project_management.controller;

import com.ayoub.project_management.model.Comment;
import com.ayoub.project_management.model.User;
import com.ayoub.project_management.request.createCommentRequest;
import com.ayoub.project_management.responce.MessageResponse;
import com.ayoub.project_management.service.comment.CommentService;
import com.ayoub.project_management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody createCommentRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Comment createdComment =commentService.createComment(req.getIssueId(),user.getId(), req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId,user.getId());
        MessageResponse response = new MessageResponse("Comment deleted successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable("issueId") Long issueId) throws Exception {
        List<Comment> comment = commentService.findCommentByIssueId(issueId);
        return ResponseEntity.ok(comment);
    }
}
