package org.chiches.foodrootservir.controllers.websockets;

import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.TokenDto;
import org.chiches.foodrootservir.services.DishItemService;
import org.chiches.foodrootservir.services.impl.DishItemServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class TestController {
    private final DishItemService dishItemService;

    public TestController(DishItemService dishItemService) {
        this.dishItemService = dishItemService;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/ooo")
    public ResponseEntity<?> gogo(TokenDto tokenDto){
        return dishItemService.findAll();
    }
}
