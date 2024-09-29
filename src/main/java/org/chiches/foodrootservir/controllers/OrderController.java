package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
