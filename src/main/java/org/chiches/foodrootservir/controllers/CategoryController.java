package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CategoryDTO> createCategory(@ModelAttribute CategoryDTO categoryDTO) {
        ResponseEntity<CategoryDTO> responseEntity;
        responseEntity = categoryService.createCategory(categoryDTO);
        return responseEntity;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") Long id) {
        ResponseEntity<CategoryDTO> responseEntity = categoryService.findById(id);
        return responseEntity;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam boolean active) {
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryService.findAll(active);
        return responseEntity;
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CategoryDTO> updateCategory(@ModelAttribute CategoryDTO categoryDTO) {
        ResponseEntity<CategoryDTO> responseEntity = categoryService.updateCategory(categoryDTO);
        return responseEntity;
    }

    @PostMapping(path = "/upload-picture")
    public ResponseEntity<UrlDTO> uploadPicture(@ModelAttribute FileUploadDTO fileUploadDTO) {
        ResponseEntity<UrlDTO> responseEntity;
        responseEntity = categoryService.uploadImage(fileUploadDTO);
        return responseEntity;
    }

}
