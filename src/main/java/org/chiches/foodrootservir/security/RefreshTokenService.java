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
    @Value("${jwt.refreshTokenExpiration}")
    private long expirationTime;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshTokenEntity createRefreshToken(String login) {
        UserEntity user = userRepository.findByLogin(login).orElseThrow();
//        refreshTokenRepository.delete(user.getRefreshToken());
        RefreshTokenEntity refreshTokenEntity = user.getRefreshToken();

        if (refreshTokenEntity != null) {
            refreshTokenRepository.delete(refreshTokenEntity);
        }

        RefreshTokenEntity refreshToken = new RefreshTokenEntity(
                user,
                UUID.randomUUID().toString(),
                Instant.now().plusMillis(expirationTime)
        );
        refreshTokenRepository.save(refreshToken);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return refreshToken;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            return null;
        }
        return refreshToken;
    }

    public boolean extendToken(RefreshTokenEntity refreshTokenEntity) {
  //TODO: IMPLEMENT EXTEND TOKEN
        if (verifyExpiration(refreshTokenEntity) != null) {
            System.out.println("Token was: " + refreshTokenEntity.getExpiryDate());
            refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(expirationTime));
            refreshTokenRepository.save(refreshTokenEntity);
            System.out.println("Token now: " + refreshTokenEntity.getExpiryDate());
            return true;
        }
        return false;
    }

    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
