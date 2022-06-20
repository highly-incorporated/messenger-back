package com.corporate.rest;

import com.corporate.dto.AuthenticationRequestDto;
import com.corporate.dto.JwtDto;
import com.corporate.dto.JwtRefreshDto;
import com.corporate.dto.RegistrationRequestDto;
import com.corporate.jwt.JWTProvider;
import com.corporate.model.User;
import com.corporate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private JwtDto jwtDto;

    @Autowired User user;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        String email = authenticationRequestDto.getEmail();
        String password = authenticationRequestDto.getPassword();
        user = userService.findByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            return ResponseEntity.notFound().build();
        }

        jwtDto.setAccessToken(jwtProvider.generateAccessToken(user));
        jwtDto.setRefreshToken(jwtProvider.generateRefreshToken(user));
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        String email = registrationRequestDto.getEmail();
        String password = registrationRequestDto.getPassword();
        String name = registrationRequestDto.getName();
        String avatar = registrationRequestDto.getAvatar();

        if (userService.findByEmail(email) != null) {
            return ResponseEntity.notFound().build();
        }

        user = userService.saveUser(new User(email, password, name, avatar));

        jwtDto.setAccessToken(jwtProvider.generateAccessToken(user));
        jwtDto.setRefreshToken(jwtProvider.generateRefreshToken(user));
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("token")
    public ResponseEntity token(@RequestBody JwtRefreshDto jwtRefreshDto) {
        if (!jwtProvider.validateRefreshToken(jwtRefreshDto.getRefreshToken())) {
            return ResponseEntity.notFound().build();
        }

        user = userService.findByUsername(jwtProvider.getRefreshClaims(jwtRefreshDto.getRefreshToken()).getSubject());
        jwtDto.setAccessToken(jwtProvider.generateAccessToken(user));
        jwtDto.setRefreshToken(jwtProvider.generateRefreshToken(user));
        return ResponseEntity.ok(jwtDto);
    }
}