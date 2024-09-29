package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.DishItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishItemService {
    ResponseEntity<DishItemDTO> createDishItem(DishItemDTO dishItemDTO);

    ResponseEntity<DishItemDTO> findById(Long id);

    ResponseEntity<List<DishItemDTO>> findAll();

    ResponseEntity<DishItemDTO> update(DishItemDTO dishItemDTO);

}