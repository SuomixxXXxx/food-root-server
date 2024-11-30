package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@RequestMapping(path = "categories")
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestPart CategoryDTO categoryDTO,
                                                      @RequestPart MultipartFile previewPicture,
                                                      @RequestPart MultipartFile mainPicture) {
        CategoryDTO savedDTO = categoryService.createCategory(categoryDTO, previewPicture, mainPicture);
        return ResponseEntity.ok(savedDTO);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        CategoryDTO savedDTO = categoryService.findById(id);
        return ResponseEntity.ok(savedDTO);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam boolean active) {
        List<CategoryDTO> categoryDTOS = categoryService.findAll(active);
        return ResponseEntity.ok(categoryDTOS);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestPart CategoryDTO categoryDTO,
                                                      @RequestPart MultipartFile previewPicture,
                                                      @RequestPart MultipartFile mainPicture) {
        CategoryDTO savedDTO = categoryService.updateCategory(categoryDTO, previewPicture, mainPicture);
        return ResponseEntity.ok(savedDTO);
    }


}
