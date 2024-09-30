package org.chiches.foodrootservir.controller;

import org.chiches.foodrootservir.dto.TokenDto;
import org.chiches.foodrootservir.dto.UserDto;
import org.chiches.foodrootservir.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestParam String refreshToken){
        return ResponseEntity.ok(authenticationService.refresh(refreshToken));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate(@RequestParam String login, @RequestParam String password){
        return ResponseEntity.ok(authenticationService.authenticate(login, password));
    }
}
