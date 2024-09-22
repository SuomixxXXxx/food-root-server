package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.DishItemEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DishItemRepository extends GeneralRepository<DishItemEntity, Long> {
}
