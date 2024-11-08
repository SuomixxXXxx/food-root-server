package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DishItemService {
    ResponseEntity<DishItemDTO> createDishItem(DishItemDTO dishItemDTO);

    ResponseEntity<DishItemDTO> findById(Long id);

    ResponseEntity<List<DishItemDTO>> findAll();

    ResponseEntity<DishItemDTO> update(DishItemDTO dishItemDTO);

    ResponseEntity<List<DishItemDTO>> getAllByCategory(Long categoryId);

    ResponseEntity<List<DishItemDTO>> getAllByName(String name);

    void delete(Long id);

    ResponseEntity<UrlDTO> uploadImage(FileUploadDTO fileUploadDTO);

}
