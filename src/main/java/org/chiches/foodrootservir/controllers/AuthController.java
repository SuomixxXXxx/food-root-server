package org.chiches.foodrootservir.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.chiches.foodrootservir.dto.TokenDTO;
import org.chiches.foodrootservir.dto.UserDTO;
import org.chiches.foodrootservir.services.impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    public AuthController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody UserDTO userDto) {
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authenticationService.refresh(refreshToken));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        //System.out.println("login: " + login + " password: " + password);

        TokenDTO tokenDTO = authenticationService.authenticate(login, password);

        Cookie jwtToken = new Cookie("jwtToken", "Bearer " + tokenDTO.getToken());
        //jwtToken.setHttpOnly(true);
        jwtToken.setMaxAge(60 * 30);
        jwtToken.setPath("/api");
        response.addCookie(jwtToken);

        Cookie refreshToken = new Cookie("refreshToken", tokenDTO.getRefreshToken());
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(86400); //seconds, not milliseconds
        refreshToken.setPath("/api");
        response.addCookie(refreshToken);

        return ResponseEntity.ok(tokenDTO);
    }
}
