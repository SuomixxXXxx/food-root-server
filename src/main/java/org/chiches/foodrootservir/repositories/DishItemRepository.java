package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface DishItemRepository extends GeneralRepository<DishItemEntity, Long> {
    List<DishItemEntity> findAllByCategory(CategoryEntity categoryEntity);

    List<DishItemEntity> findAllByNameContainingIgnoreCase(String name);
}
