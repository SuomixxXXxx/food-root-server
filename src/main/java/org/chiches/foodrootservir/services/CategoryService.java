package org.chiches.foodrootservir.services;

import org.apache.coyote.Response;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO, MultipartFile previewPicture, MultipartFile mainPicture);

    CategoryDTO findById(Long id);

    List<CategoryDTO> findAll(boolean active);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, MultipartFile previewPicture, MultipartFile mainPicture);

}
