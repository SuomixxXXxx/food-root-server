package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends GeneralRepository<AuthorityEntity, Long>{
    boolean existsByAuthority(String authority);

    List<AuthorityEntity> findByAuthorityContainingIgnoreCase(String authority);
}
