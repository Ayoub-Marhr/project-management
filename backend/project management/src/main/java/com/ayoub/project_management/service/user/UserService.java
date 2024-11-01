package com.ayoub.project_management.service.user;

import com.ayoub.project_management.model.User;

public interface UserService {
    User findUserProfileByJwt (String jwt)throws Exception;
    User findUserByEmail(String email)throws Exception;
    User findUserById(Long id)throws Exception;
    User updateUserProfile(User user,int number)throws Exception;
}
