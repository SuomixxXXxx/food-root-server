package org.chiches.foodrootservir.repositories;

import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.entities.OrderContentEntity;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderContentRepository extends GeneralRepository<OrderContentEntity, Long>{
    @Query(value = "select sum(oc.quantity) from OrderContentEntity oc " +
            "where oc.dishItem = :dishItemEntity and " +
            "oc.order in :orders")
    Integer findCount(@Param("dishItemEntity") DishItemEntity dishItemEntity, @Param("orders")List<OrderEntity> orders);
}
