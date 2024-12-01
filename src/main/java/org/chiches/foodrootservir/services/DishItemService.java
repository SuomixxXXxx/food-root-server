package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
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

}
