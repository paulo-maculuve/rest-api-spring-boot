package com.maculuve.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maculuve.data.dto.v1.security.AccountCredentialsDTO;
import com.maculuve.data.dto.v1.security.TokenDTO;
import com.maculuve.repositories.UserRepository;
import com.maculuve.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    // @Autowired
    // private AuthenticationManager authenticationManager;
    // @Autowired
    // private JwtTokenProvider jwtTokenProvider;
    // @Autowired
    // private UserRepository userRepository;

    // @SuppressWarnings("rawtypes")
    // public ResponseEntity signin(AccountCredentialsDTO accountCredentialsVO) {
    //     try {
    //         var username = accountCredentialsVO.getUsername();
    //         var password = accountCredentialsVO.getPassword();
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
          
    //         var user = userRepository.findByUsername(username);
           
    //         var tokenResponse = new TokenDTO();
    //         if (user != null) {
    //             tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
    //         } else {
    //             throw new UsernameNotFoundException("Username " + username + " not found!");
    //         }
    //         return ResponseEntity.ok(tokenResponse);

    //     } catch (Exception e) {
    //         throw new BadCredentialsException("Invalid username/password supplied!");
    //     }
    // }

    // @SuppressWarnings("rawtypes")
	// public ResponseEntity refreshToken(String username, String refreshToken) {
	// 	var user = userRepository.findByUsername(username);
		
	// 	var tokenResponse = new TokenDTO();
	// 	if (user != null) {
	// 		tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
	// 	} else {
	// 		throw new UsernameNotFoundException("Username " + username + " not found!");
	// 	}
	// 	return ResponseEntity.ok(tokenResponse);
	// }
    

}
