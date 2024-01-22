package com.alex.gamestore.controller;

import com.alex.gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
//        try {
//            userService.registerUser(registrationRequest);
//            return ResponseEntity.ok("User registered successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed.");
//        }
//    }
}
