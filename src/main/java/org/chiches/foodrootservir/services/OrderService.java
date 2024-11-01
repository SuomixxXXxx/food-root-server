package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO);

    public ResponseEntity<OrderDTO> updateOrderStatus(Long id, OrderStatus orderStatus);

    public ResponseEntity<OrderDTO> findById(Long id);

    public ResponseEntity<?> findAllActive();
}
