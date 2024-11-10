package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.OrderContentDTO;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.*;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.InvalidArgumentException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.misc.PublicDestination;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.repositories.UserRepository;
import org.chiches.foodrootservir.services.NotificationService;
import org.chiches.foodrootservir.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishItemRepository dishItemRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, DishItemRepository dishItemRepository, UserRepository userRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.dishItemRepository = dishItemRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with login " + userDetails.getUsername() + " not found"));
        OrderEntity orderEntity = new OrderEntity(userEntity);
        for (OrderContentDTO orderContentDTO : orderDTO.getOrderContentDTOs()) {
            Long dishItemId = orderContentDTO.getDishItemDTO().getId();
            DishItemEntity dishItemEntity = dishItemRepository.findById(dishItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dish item with id " + dishItemId + " not found"));
            orderEntity.setOrderContent(dishItemEntity, orderContentDTO.getQuantity());
        }
        try {
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            OrderDTO savedOrderDTO = convert(savedOrderEntity);
            ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);

            notifyWebSocketClients();

            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException("The order was not created due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<OrderDTO> findById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        OrderDTO savedOrderDTO = convert(orderEntity);
        ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<?> findAllActive() {
        List<OrderDTO> activeOrders = orderRepository.findByStatus(OrderStatus.CREATED).stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activeOrders);
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> updateOrderStatus(Long id, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        orderEntity.changeStatus(orderStatus);
        try {
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            OrderDTO savedOrderDTO = convert(savedOrderEntity);
            ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);

            notifyWebSocketClients();

            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException("The order was not canceled due to problems connecting to the database");
        }
    }

    private OrderDTO convert(OrderEntity orderEntity) {
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
            List<OrderContentDTO> orderContentDTOs = orderEntity.getOrderContents().stream()
                    .map(orderContentEntity -> {
                        OrderContentDTO orderContentDTO = modelMapper.map(orderContentEntity, OrderContentDTO.class);
                        DishItemDTO dishItemDTO = modelMapper.map(orderContentEntity.getDishItem(), DishItemDTO.class);
                        orderContentDTO.setDishItemDTO(dishItemDTO);
                        return orderContentDTO;
                    })
                    .collect(Collectors.toList());

            orderDTO.setOrderContentDTOs(orderContentDTOs);
        return orderDTO;
    }

    private void notifyWebSocketClients() {
        List<OrderDTO> activeOrders = orderRepository.findByStatus(OrderStatus.CREATED).stream()
                .map(this::convert)
                .collect(Collectors.toList());
        notificationService.sentToAll(PublicDestination.ORDER_UPDATE, ResponseEntity.ok(activeOrders));
    }
}
