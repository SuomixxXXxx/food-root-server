package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.OrderDTO;

import java.util.List;

public interface MessagesService {
    public List<OrderDTO> findAllActive();
}
