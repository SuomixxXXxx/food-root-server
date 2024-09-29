package org.chiches.foodrootservir.services;

import org.apache.coyote.Response;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO);

    ResponseEntity<CategoryDTO> findById(Long id);

    ResponseEntity<List<CategoryDTO>> findAll();

    ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO);
}
