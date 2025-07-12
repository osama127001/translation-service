package com.osama.translation_service.controller;

import com.osama.translation_service.dao.UserDao;
import com.osama.translation_service.domain.User;
import com.osama.translation_service.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDao userDao;
    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user) {
        User userToRegister = new User();
        userToRegister.setName(user.getName());
        userToRegister.setEmail(user.getEmail());
        userToRegister.setRole(user.getRole());
        userToRegister.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(userToRegister);
        String jwt = jwtService.generateJwt(userToRegister);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody @Valid User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        User userToAuthenticate = userDao.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(user.getEmail()));
        String jwt = jwtService.generateJwt(userToAuthenticate);
        return ResponseEntity.ok(jwt);
    }
}
