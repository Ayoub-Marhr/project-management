package com.ayoub.project_management.controller;

import com.ayoub.project_management.model.*;
import com.ayoub.project_management.request.InvitationRequest;
import com.ayoub.project_management.responce.MessageResponse;
import com.ayoub.project_management.service.invitation.InvitationService;
import com.ayoub.project_management.service.project.ProjectService;
import com.ayoub.project_management.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        List<Project> projects=projectService.getAllProjectsByTeam(user,category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @GetMapping("/{prjectId}")
    public ResponseEntity <Project> getProjects(
           @PathVariable Long prjectId,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        Project projects=projectService.getProjectById(prjectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity <Project> createProject(
            @RequestBody Project project,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        Project Createdproject=projectService.createProject(project,user);
        return new ResponseEntity<>(Createdproject, HttpStatus.OK);
    }
    @PatchMapping("/{projectId}")
    public ResponseEntity <Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project project,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        Project updatedproject=projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updatedproject, HttpStatus.OK);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity <MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("authorization") String token) throws Exception {
        User user= userService.findUserProfileByJwt(token);
        projectService.deleteProject(projectId,user.getId());
        MessageResponse res = new MessageResponse("project Deleted Successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false) String keyword,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
       List<Project>  projects=projectService.searchProject(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @GetMapping("/{prjectId}/chat")
    public ResponseEntity <Chat> getChatbyProjectId(
            @PathVariable Long prjectId,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        Chat chat=projectService.getChatById(prjectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    @PostMapping("/invite")
    public ResponseEntity <MessageResponse> inviteProject(
            @RequestBody InvitationRequest req,
            @RequestParam Project project,
            @RequestHeader("authorization") String token) throws Exception {


        User user= userService.findUserProfileByJwt(token);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());
        MessageResponse res = new MessageResponse("Invitation sent Successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/acceptInvitation")
    public ResponseEntity <Invitation> acceptInviteProject(
            @RequestBody String token,
            @RequestParam Project project,
            @RequestHeader("authorization") String jwt) throws Exception {


        User user= userService.findUserProfileByJwt(jwt);
       Invitation invitation = invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
