package com.demo.user;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/{dbName}/{tableName}")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest,
                                          @PathVariable String dbName,
                                          @PathVariable String tableName) {
        userService.registerUser(registrationRequest, dbName, tableName);
        return ResponseEntity.ok("User successfully registered");
    }

    // @PostMapping("/login/{dbName}/{tableName}")
    // public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest,
    //                                    @PathVariable String dbName,
    //                                    @PathVariable String tableName) throws SQLException {
    //     boolean response = userService.loginUser(loginRequest, dbName, tableName);
    //     return response ? ResponseEntity.ok("Login successful") : ResponseEntity.status(401).body("Invalid credentials");
    // }
}