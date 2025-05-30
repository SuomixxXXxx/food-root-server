package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishItemRepository extends GeneralRepository<DishItemEntity, Long> {

    Page<DishItemEntity> findAllByCategoryAndIsDeletedFalse(CategoryEntity categoryEntity, Pageable pageable);

    List<DishItemEntity> findAllByNameContainingIgnoreCaseAndIsDeletedFalse(String name);

    @Query(value = "select d from DishItemEntity d where d.isDeleted = false")
    List<DishItemEntity> findAll();

    @Query(value = "select d from DishItemEntity d where d.id = :id and d.isDeleted = false")
    Optional<DishItemEntity> findById(@RequestParam(value = "id") Long id);
}
