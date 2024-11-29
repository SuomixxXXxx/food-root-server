package org.chiches.foodrootservir.services;

import org.apache.coyote.Response;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO findById(Long id);

    List<CategoryDTO> findAll(boolean active);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    ResponseEntity<UrlDTO> uploadImage(FileUploadDTO fileUploadDTO);
}
