package com.corporate.rest;

import com.corporate.exceptions.InvalidTokenExceptions;
import com.corporate.jwt.JWTProvider;
import com.corporate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/adding/")
public class AddingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping("userSearch/{username}")
    public ResponseEntity<?> findUsers(HttpServletRequest request, @PathVariable String username) {

        return ResponseEntity.ok(userRepository.findUsers(username));
    }
}
