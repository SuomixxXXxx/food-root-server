package org.chiches.foodrootservir.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.services.MessagesService;
import org.chiches.foodrootservir.services.OrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MessagesService messagesService;
    private final List<WebSocketSession> sessions = new ArrayList<>();

    public OrderWebSocketHandler(ObjectMapper objectMapper, MessagesService messagesService) {
        this.objectMapper = objectMapper;
        this.messagesService = messagesService;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        List<OrderDTO> orderDTOs = messagesService.findAllActive();
        String message = objectMapper.writeValueAsString(orderDTOs);
        session.sendMessage(new TextMessage(message));
        System.out.println("new session " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("closed session " + session.getId());
    }

    public void updateOrdersList(List<OrderDTO> orders) {
        for (WebSocketSession socketSession : sessions) {
            try {
                String message = objectMapper.writeValueAsString(orders);
                socketSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
