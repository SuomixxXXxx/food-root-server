package org.chiches.foodrootservir.service;

import org.chiches.foodrootservir.dto.TokenDto;
import org.chiches.foodrootservir.dto.UserDto;
import org.chiches.foodrootservir.entities.RefreshTokenEntity;
import org.chiches.foodrootservir.entities.UserEntity;
import org.chiches.foodrootservir.repositories.UserRepository;
import org.chiches.foodrootservir.security.JwtService;
import org.chiches.foodrootservir.security.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenDto authenticate(String email, String password) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        email,
//                        password
//                )
//        );
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtService.generateToken(userDetails);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return new TokenDto(token, refreshToken.getToken());

    }

    public TokenDto register(UserDto userDto){
        if (userRepository.existsByLogin(userDto.getLogin())){
            throw new IllegalArgumentException("User with login " + userDto.getLogin() + " already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userDto.getLogin());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userRepository.save(userEntity);
        String token = jwtService.generateToken(userEntity);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userEntity.getLogin());
        return new TokenDto(token, refreshToken.getToken());
    }
    public TokenDto refresh(String refreshToken){
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.verifyExpiration(refreshTokenService.findByToken(refreshToken).orElseThrow());
        //todo: catch and all of that

        UserEntity userEntity = refreshTokenEntity.getUser();
        String token = jwtService.generateToken(userEntity);

        return new TokenDto(token, refreshToken);
    }
}
