package org.chiches.foodrootservir.controllers.websockets;

import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.services.DishItemService;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final OrderService orderService;

    public WebSocketController(OrderService orderService) {
        this.orderService = orderService;
    }
    // subscribe to /ordersub/active-orders, will provide active orders on connection and on creation

    // for message mapping append /orders
    @MessageMapping("/getActiveOrders") // /orders/getActiveOrders
    @SendTo("/ordersub/active-orders")
    public ResponseEntity<?> getDishItems(){
        System.out.println("getDishItems");
        return orderService.findAllActive();
    }
}
