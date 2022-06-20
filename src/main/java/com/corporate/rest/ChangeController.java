package com.corporate.rest;

import com.corporate.dto.ChangeUserDataDto;
import com.corporate.dto.JwtDto;
import com.corporate.exceptions.InvalidTokenExceptions;
import com.corporate.jwt.JWTProvider;
import com.corporate.model.User;
import com.corporate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/change/")
public class ChangeController {

    @Autowired
    JWTProvider jwtProvider;

    @Autowired
    JwtDto jwtDto;

    @Autowired
    UserRepository userRepository;

    @PatchMapping("/userData")
    public ResponseEntity changeUserData(HttpServletRequest request, @RequestBody ChangeUserDataDto changeUserDataDto) {

        if (!jwtProvider.validateAccessToken(request.getHeader("Authorization"))) {
            throw new InvalidTokenExceptions();
        }

        User user = userRepository.findById((int) jwtProvider.getAccessClaims(request.getHeader("Authorization")).get("id"));

        String newPassword = changeUserDataDto.getNewPassword();
        String oldPassword = changeUserDataDto.getOldPassword();
        String newAva = changeUserDataDto.getNewAva();
        String newUsername = changeUserDataDto.getNewUsername();

        if (!oldPassword.equals("false") && !user.getPassword().equals(oldPassword)) {
            return ResponseEntity.notFound().build();
        }

        if (!newAva.equals("false")) {
            userRepository.updateAvatar(newAva, user.getId());
            user.setAvatar(newAva);
        }

        if (!newUsername.equals("false")) {
            userRepository.updateUsername(newUsername, user.getId());
            user.setUsername(newUsername);
        }

        if (!oldPassword.equals("false")) {
            userRepository.updatePassword(newPassword, user.getId());
            user.setPassword(newPassword);
        }

        jwtDto.setAccessToken(jwtProvider.generateAccessToken(user));
        jwtDto.setRefreshToken(jwtProvider.generateRefreshToken(user));

        return ResponseEntity.ok(jwtDto);
    }
}