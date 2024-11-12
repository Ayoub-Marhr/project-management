package com.ayoub.project_management.api.repository;

import com.ayoub.project_management.Repository.UserRepository;
import com.ayoub.project_management.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use real DB
public class UserRepositorTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void UserRepository_GetUserByEmail_ReturnUser() {
        //Arrange : Create a User and add it to database
        User user = new User();
        user.setEmail("test@test.com");
        user.setFullName("Test");
        user.setPassword("password");
        userRepository.save(user);
        //Act: Attempt to retrieve the user by email
        Optional<User> userOptional = userRepository.findByEmail("test@test.com");
        //Assert
        Assertions.assertTrue(userOptional.isPresent(),"User should be found");
        Assertions.assertEquals(user.getPassword(), userOptional.get().getPassword(), "Password should be equal");
        Assertions.assertEquals(user.getFullName(), userOptional.get().getFullName(), "Full Name should be equal");
        Assertions.assertEquals(user.getEmail(), userOptional.get().getEmail(), "Email should be equal");
    }
}
