package com.ayoub.project_management.controller;

import com.ayoub.project_management.model.Issue;
import com.ayoub.project_management.model.IssueDTO;
import com.ayoub.project_management.model.User;
import com.ayoub.project_management.request.IssueRequest;
import com.ayoub.project_management.responce.AuthResponce;
import com.ayoub.project_management.service.issue.IssueService;
import com.ayoub.project_management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable() Long issueId)throws Exception {
    return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Issue> issues = issueService.getIssueByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue, @RequestHeader("Authorization") String Token)throws Exception {

        User tokenUser =userService.findUserProfileByJwt(Token);
        User user = userService.findUserById(tokenUser.getId());


           Issue createIssue =issueService.createIssue(issue,tokenUser);
           IssueDTO issueDTO = new IssueDTO();
           issueDTO.setDescription(createIssue.getDescription());
           issueDTO.setTitle(createIssue.getTitle());
           issueDTO.setAssignee(createIssue.getAssignee());
           issueDTO.setPriority(createIssue.getPriority());
           issueDTO.setProjectId(createIssue.getProjectId());
           issueDTO.setDueDate(createIssue.getDueDate());
           issueDTO.setTags(createIssue.getTags());
           issueDTO.setStatus(createIssue.getStatus());
           issueDTO.setProject(createIssue.getProject());
           issueDTO.setId(createIssue.getId());
           return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<AuthResponce> deleteIssue(@PathVariable Long issueId,@RequestHeader("Authorization") String Token)throws Exception {
        User tokenUser =userService.findUserProfileByJwt(Token);
        issueService.deleteIssue(issueId,tokenUser.getId());
        AuthResponce authResponce = new AuthResponce();
        authResponce.setMessage("Successfully deleted");
        return ResponseEntity.ok(authResponce);
    }
    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,@PathVariable Long userId)throws Exception {
        Issue issue = issueService.addUserToIssue(issueId,userId);
        return ResponseEntity.ok(issue);
    }
    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue>updateIssueStatus(@PathVariable Long issueId,@PathVariable String status)throws Exception {
        Issue issue =issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issue);}

}
