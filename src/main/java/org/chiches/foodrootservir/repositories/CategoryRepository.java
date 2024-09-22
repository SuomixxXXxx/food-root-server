package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends GeneralRepository<CategoryEntity, Long> {
}
