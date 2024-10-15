package org.chiches.foodrootservir.services.impl;

import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.OrderContentDTO;
import org.chiches.foodrootservir.dto.OrderDTO;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.chiches.foodrootservir.entities.OrderStatus;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.services.MessagesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesServiceImpl implements MessagesService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public MessagesServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<OrderDTO> findAllActive() {
        List<OrderEntity> orderEntities = orderRepository.findByStatus(OrderStatus.CREATED);
        List<OrderDTO> orderDTOs = orderEntities.stream()
                .map(this::convert)
                .toList();
        return orderDTOs;
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
                .toList();

        orderDTO.setOrderContentDTOs(orderContentDTOs);
        return orderDTO;
    }
}
