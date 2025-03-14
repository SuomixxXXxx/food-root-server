package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrderStatus(Long id, OrderStatus orderStatus);

    OrderDTO findById(Long id);

    List<OrderDTO> findAllActive();
}
