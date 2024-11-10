package org.chiches.foodrootservir.controllers.websockets;

import org.chiches.foodrootservir.dto.TokenDTO;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final OrderService orderService;

    public WebSocketController(OrderService orderService) {
        this.orderService = orderService;
    }
    // subscribe to /ordersub/active-orders, will provide active orders on connection and on creation

    // for message mapping append /orders
    //@MessageMapping("/getActiveOrders") // /orders/getActiveOrders
    //@SendTo("/ordersub/active-orders")
    public ResponseEntity<?> getDishItems(){
        System.out.println("getDishItems");
        return orderService.findAllActive();
    }
    @MessageMapping("/getActiveOrders") // topic to send to  /orders/getActiveOrders
    @SendToUser("/ordersub/active-orders") // append /user/ before endpoint, for example /user/ordersub/active-orders
    public ResponseEntity<?> getActiveOrder(TokenDTO tokenDTO){
        return orderService.findAllActive();
    }
}
