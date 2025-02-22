package com.maculuve.controllers.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.maculuve.data.dto.v1.security.AccountCredentialsDTO;

import io.swagger.v3.oas.annotations.Operation;

public interface AuthControllerDocs {
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authentication a user and retorns a token")
    public ResponseEntity sigin(@RequestBody AccountCredentialsDTO accountCredentialsVO);

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticated user and returns a token")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
            @RequestHeader("Authorization") String refreshToken);

}