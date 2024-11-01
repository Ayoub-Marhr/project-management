package com.ayoub.project_management.service.user;

import com.ayoub.project_management.Repository.UserRepository;
import com.ayoub.project_management.config.JwtProvider;
import com.ayoub.project_management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        Optional<User> Optionaluser = userRepository.findByEmail(email);
        return Optionaluser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @Override
    public User findUserById(Long id) throws Exception {
        Optional<User> Optionaluser = userRepository.findById(id);
        return Optionaluser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @Override
    public User updateUserProfile(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
