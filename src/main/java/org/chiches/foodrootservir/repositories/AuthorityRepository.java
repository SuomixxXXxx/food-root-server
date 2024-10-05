package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.AuthorityEntity;

public interface AuthorityRepository extends GeneralRepository<AuthorityEntity, Long>{
    boolean existsByAuthority(String authority);
}
