package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.OrderStatus;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.createOrder(orderDTO);
        return responseEntity;
    }

    @GetMapping(path = "get/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("id") Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.findById(id);
        return responseEntity;
    }

    @PatchMapping(path = "/complete")
    public ResponseEntity<OrderDTO> completeOrder(@RequestParam Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
        return responseEntity;
    }

    @PatchMapping(path = "/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@RequestParam Long id) {
        ResponseEntity<OrderDTO> responseEntity;
        responseEntity = orderService.updateOrderStatus(id, OrderStatus.CANCELED);
        return responseEntity;
    }

}
