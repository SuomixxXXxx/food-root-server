package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.config.OrderWebSocketHandler;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.OrderContentDTO;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.*;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.InvalidArgumentException;
import org.chiches.foodrootservir.exceptions.NotEnoughStockException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.repositories.UserRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishItemRepository dishItemRepository;
    private final UserRepository userRepository;
    private final OrderWebSocketHandler orderWebSocketHandler;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, DishItemRepository dishItemRepository, UserRepository userRepository, OrderWebSocketHandler orderWebSocketHandler) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.dishItemRepository = dishItemRepository;
        this.userRepository = userRepository;
        this.orderWebSocketHandler = orderWebSocketHandler;
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        List<OrderContentEntity> orderContentEntities = new ArrayList<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with login " + userDetails.getUsername() + " not found"));
        Double price = 0d;
        for (OrderContentDTO orderContentDTO : orderDTO.getOrderContentDTOs()) {
            Long dishItemId = orderContentDTO.getDishItemDTO().getId();
            DishItemEntity dishItemEntity = dishItemRepository.findById(dishItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dish item with id " + dishItemId + " not found"));
            if (dishItemEntity.getQuantity() < orderContentDTO.getQuantity()) {
                throw new NotEnoughStockException("Not enough stock for " + dishItemEntity.getName());
            }
            OrderContentEntity orderContentEntity = new OrderContentEntity(
                    dishItemEntity,
                    orderContentDTO.getQuantity()
            );
            orderContentEntities.add(orderContentEntity);
            price += dishItemEntity.getPrice() * orderContentEntity.getQuantity();
        }
        price = Math.floor(price * 100) / 100;
        OrderEntity orderEntity = new OrderEntity(userEntity,
                OrderStatus.CREATED,
                price,
                LocalDateTime.now(),
                orderContentEntities);
        for (OrderContentEntity orderContentEntity : orderEntity.getOrderContents()) {
            orderContentEntity.setOrder(orderEntity);
        }
        try {
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            OrderDTO savedOrderDTO = convert(savedOrderEntity);
            ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
            List<OrderDTO> activeOrders = orderRepository.findByStatus(OrderStatus.CREATED).stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
            orderWebSocketHandler.updateOrdersList(activeOrders);
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
    public ResponseEntity<OrderDTO> updateOrderStatus(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderDTO.getId() + " not found"));
        if (orderDTO.getStatus().equals(OrderStatus.CANCELED)) {
            orderEntity.setStatus(OrderStatus.CANCELED);
        } else if (orderDTO.getStatus().equals(OrderStatus.COMPLETED)) {
            orderEntity.setStatus(OrderStatus.COMPLETED);
            orderEntity.setDateOfCompletion(LocalDateTime.now());
        } else {
            throw new InvalidArgumentException("Invalid order status");
        }

        try {
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            OrderDTO savedOrderDTO = convert(savedOrderEntity);
            ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
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

}
