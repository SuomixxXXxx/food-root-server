package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends GeneralRepository<OrderEntity, Long> {
}
