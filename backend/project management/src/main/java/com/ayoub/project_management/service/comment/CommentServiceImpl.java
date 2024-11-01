package com.ayoub.project_management.service.comment;

import com.ayoub.project_management.Repository.CommentRepository;
import com.ayoub.project_management.Repository.IssueRepository;
import com.ayoub.project_management.Repository.UserRepository;
import com.ayoub.project_management.model.Comment;
import com.ayoub.project_management.model.Issue;
import com.ayoub.project_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(issueOptional.isEmpty()){
            throw new Exception("Issue not found with issue id " + issueId );
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with userid " + userId );
        }
        Issue issue = issueOptional.get();
        User user = userOptional.get();
        Comment comment = new Comment();

        comment.setIssue(issue);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentOptional.isEmpty()){
            throw new Exception("Comment not found with issue id " + commentId );
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with userid " + userId );
        }
        Comment comment = commentOptional.get();
        User user = userOptional.get();
        if(comment.getUser().getId().equals(user.getId())){
            commentRepository.delete(comment);
        }else {
            throw new Exception("Comment not deleted from issue id " + commentId );
        }

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {

        return commentRepository.findCommentByIssueId(issueId);
    }
}
