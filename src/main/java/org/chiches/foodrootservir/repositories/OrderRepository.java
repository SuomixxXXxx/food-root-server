package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.OrderEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends GeneralRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByDateOfCreationBetween(LocalDateTime start, LocalDateTime finish);
}
