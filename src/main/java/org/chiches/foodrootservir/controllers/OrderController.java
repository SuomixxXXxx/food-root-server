package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.OrderStatus;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(order);
    }

    @GetMapping(path = "get/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") Long id) {
        OrderDTO order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PatchMapping(path = "/complete")
    public ResponseEntity<OrderDTO> completeOrder(@RequestParam Long id) {
        OrderDTO order = orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
        return ResponseEntity.ok(order);
    }

    @PatchMapping(path = "/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@RequestParam Long id) {
        OrderDTO order = orderService.updateOrderStatus(id, OrderStatus.CANCELED);
        return ResponseEntity.ok(order);
    }

}
