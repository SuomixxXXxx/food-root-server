package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO);

    public ResponseEntity<OrderDTO> updateOrderStatus(OrderDTO orderDTO);

    public ResponseEntity<OrderDTO> findById(Long id);
}
