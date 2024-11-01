package com.ayoub.project_management.service.project;

import com.ayoub.project_management.model.Chat;
import com.ayoub.project_management.model.Project;
import com.ayoub.project_management.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project,User user)throws Exception;
    List<Project> getAllProjectsByTeam(User user,String category,String tag )throws Exception;
    Project getProjectById(Long projectId)throws Exception;
    void deleteProject(Long projectId , Long UserId)throws Exception;
    Project updateProject(Project project, Long Id)throws Exception;
    void addUserToProject(Long projectId, Long UserId)throws Exception;
    void removeUserToProject(Long projectId, Long UserId)throws Exception;
    List<Project> searchProject(String keyword,User user)throws Exception;
    Chat getChatById(Long chatId)throws Exception;

}
