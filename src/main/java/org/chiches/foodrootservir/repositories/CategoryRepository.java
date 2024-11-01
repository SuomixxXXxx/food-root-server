package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends GeneralRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByDishItemsIsNotEmpty();

}
