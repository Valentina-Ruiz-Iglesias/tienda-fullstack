package com.tienda.tienda_backend.controller;

import com.tienda.tienda_backend.dto.LoginRequest;
import com.tienda.tienda_backend.dto.RegisterRequest;
import com.tienda.tienda_backend.entity.User;
import com.tienda.tienda_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // se encripta en el service
        user.setDateOfBirth(request.getDateOfBirth());
        user.setIsDuocStudent(Boolean.TRUE.equals(request.getIsDuocStudent()));
        user.setHasFelices50(false); // por defecto
        user.setRole("CLIENT"); // por defecto

        User saved = userService.createUser(user);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> optionalUser = userService.getUserByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = optionalUser.get();

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // devolvemos solo el user; luego aquí metemos JWT y armamos { token, user: {...} }
        return ResponseEntity.ok(user);
    }
}
