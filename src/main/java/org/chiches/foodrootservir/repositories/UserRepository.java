package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GeneralRepository<UserEntity, Long> {
}
