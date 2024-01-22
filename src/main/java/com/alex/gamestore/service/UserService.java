package com.alex.gamestore.service;

import com.alex.gamestore.exceptions.UserNotFoundException;
import com.alex.gamestore.jpa.UserRepository;
import com.alex.gamestore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(User user) {
        return userRepository.save(user);
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));


        return true;
    }
}
