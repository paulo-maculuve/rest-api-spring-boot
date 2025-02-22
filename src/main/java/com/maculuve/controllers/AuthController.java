package com.maculuve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maculuve.controllers.docs.AuthControllerDocs;
import com.maculuve.data.dto.v1.security.AccountCredentialsDTO;
import com.maculuve.services.AuthService;
import com.maculuve.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthService authService;

    @Autowired
    UserService service;

    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/signin")
    @Override
    public ResponseEntity sigin(@RequestBody AccountCredentialsDTO accountCredentialsVO) {
        if (checkIfParamsIsNotNull(accountCredentialsVO))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.signin(accountCredentialsVO);
        if (token == null)
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    @SuppressWarnings("rawtypes")
    @PutMapping(value = "/refresh/{username}")
    @Override
    public ResponseEntity refreshToken(@PathVariable("username") String username,
            @RequestHeader("Authorization") String refreshToken) {
        if (checkIfParamsIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.refreshToken(username, refreshToken);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() ||
                username == null || username.isBlank();
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialsDTO accountCredentialsVO) {
        return accountCredentialsVO == null || accountCredentialsVO.getUsername() == null
                || accountCredentialsVO.getPassword() == null || accountCredentialsVO.getPassword().isBlank();
    }

}
