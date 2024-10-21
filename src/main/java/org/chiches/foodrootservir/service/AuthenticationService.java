package org.chiches.foodrootservir.service;

import org.chiches.foodrootservir.dto.TokenDto;
import org.chiches.foodrootservir.dto.UserDto;
import org.chiches.foodrootservir.entities.AuthorityEntity;
import org.chiches.foodrootservir.entities.RefreshTokenEntity;
import org.chiches.foodrootservir.entities.UserEntity;
import org.chiches.foodrootservir.repositories.AuthorityRepository;
import org.chiches.foodrootservir.repositories.UserRepository;
import org.chiches.foodrootservir.security.JwtService;
import org.chiches.foodrootservir.security.RefreshTokenService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository authorityRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, AuthorityRepository authorityRepository, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authorityRepository = authorityRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public TokenDto authenticate(String email, String password) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        email,
//                        password
//                )
//        );
        simpMessagingTemplate.convertAndSend("/topic/ooo", userRepository.findByLogin(email).get());
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
        List<AuthorityEntity> userDefault = authorityRepository.findDefaultUserAuthority();
        userEntity.setAuthorities(userDefault);
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
