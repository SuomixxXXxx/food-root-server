package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.*;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishItemService {
    DishItemDTO createDishItem(DishItemDTO dishItemDTO);

    DishItemDTO findById(Long id);

    List<DishItemDTO> findAll();

    DishItemDTO update(DishItemDTO dishItemDTO);

    List<DishItemDTO> getAllByCategory(Long categoryId, int page, int size);

    List<DishItemDTO> getAllByName(String name);

    void delete(Long id);

    UrlDTO uploadImage(FileUploadDTO fileUploadDTO);

    List<DishQuantityDTO> getQuantities(DishIdsDTO dishIdsDTO);

}
