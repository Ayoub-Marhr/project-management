package com.ayoub.project_management.Repository;

import com.ayoub.project_management.model.Project;
import com.ayoub.project_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
   // List<Project> findByOwner(User owner);
    List<Project> findByNameContainingIgnoreCaseAndTeamContains(String partialName,User user);
    //@Query("SELECT p from Project p JOIN p.team t where t=:user")
   // List<Project> findProjectByTeam (@Param("user") User user);
    List<Project> findByTeamContainingOrOwner(User owner, User team);
}
