package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends GeneralRepository<CategoryEntity, Long> {

    @Query("select c from CategoryEntity c join c.dishItems d where d.isDeleted = false")
    List<CategoryEntity> findAllNotDeleted();

}
