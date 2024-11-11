package org.chiches.foodrootservir.security;

import org.chiches.foodrootservir.entities.RefreshTokenEntity;
import org.chiches.foodrootservir.entities.UserEntity;
import org.chiches.foodrootservir.repositories.RefreshTokenRepository;
import org.chiches.foodrootservir.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    //@Value("${jwt.refreshTokenExpiration}")
    private long expirationTime = 1000000;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshTokenEntity createRefreshToken(String login) {
        Optional<RefreshTokenEntity>  refreshTokenEntity= refreshTokenRepository.findByUserLogin(login);

        refreshTokenEntity.ifPresent(refreshTokenRepository::delete);

        RefreshTokenEntity refreshToken = new RefreshTokenEntity(
                userRepository.findByLogin(login).get(),
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(expirationTime)

        );

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            return null;
        }
        return refreshToken;
    }

    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
