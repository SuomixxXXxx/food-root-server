package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.dto.OrderContentDTO;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.entities.OrderContentEntity;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.NotEnoughStockException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DishItemRepository dishItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, DishItemRepository dishItemRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.dishItemRepository = dishItemRepository;
    }

    @Transactional
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();

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
            throw new DatabaseException("The order was not created due to problems connecting to the database");
        }
    }

}
