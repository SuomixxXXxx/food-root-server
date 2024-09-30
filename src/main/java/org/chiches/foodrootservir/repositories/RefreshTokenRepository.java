package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends GeneralRepository<RefreshTokenEntity, Long> {
    void delete(RefreshTokenEntity refreshToken);
    int deleteByUserId(Long userId);
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUserLogin(String login);
}
