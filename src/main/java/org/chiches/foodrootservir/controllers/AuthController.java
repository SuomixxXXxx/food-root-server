package org.chiches.foodrootservir.controllers;

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
    public ResponseEntity<TokenDTO> register(@RequestBody UserDTO userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestParam String refreshToken){
        return ResponseEntity.ok(authenticationService.refresh(refreshToken));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(@RequestParam String login, @RequestParam String password){
        System.out.println("login: " + login + " password: " + password);
        return ResponseEntity.ok(authenticationService.authenticate(login, password));
    }
}
