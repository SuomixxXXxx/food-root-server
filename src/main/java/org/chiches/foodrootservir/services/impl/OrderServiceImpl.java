package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.dto.OrderContentDTO;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.entities.OrderContentEntity;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.chiches.foodrootservir.entities.UserEntity;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.NotEnoughStockException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.repositories.UserRepository;
import org.chiches.foodrootservir.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishItemRepository dishItemRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, DishItemRepository dishItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.dishItemRepository = dishItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderContents(new ArrayList<>());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with login " + userDetails.getUsername() + " not found"));
        orderEntity.setUser(userEntity);
        Double price = 0d;
        for(OrderContentDTO orderContentDTO : orderDTO.getOrderContentDTOs()) {
            Long dishItemId = orderContentDTO.getDishItemDTO().getId();
            DishItemEntity dishItemEntity = dishItemRepository.findById(dishItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Dish item with id " + dishItemId + " not found"));
            if (dishItemEntity.getQuantity() < orderContentDTO.getQuantity()) {
                throw new NotEnoughStockException("Not enough stock for " + dishItemEntity.getName());
            }
            OrderContentEntity orderContentEntity = new OrderContentEntity();
            orderContentEntity.setOrder(orderEntity);
            orderContentEntity.setDishItem(dishItemEntity);
            orderContentEntity.setQuantity(orderContentDTO.getQuantity());
            orderEntity.getOrderContents().add(orderContentEntity);
            price += dishItemEntity.getPrice() * orderContentEntity.getQuantity();
        }
        try {
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            OrderDTO savedOrderDTO = modelMapper.map(savedOrderEntity, OrderDTO.class);
            ResponseEntity<OrderDTO> responseEntity = ResponseEntity.ok().body(savedOrderDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException("The order was not created due to problems connecting to the database");
        }
    }

}
