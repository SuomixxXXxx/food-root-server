package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
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
    public ResponseEntity<List<CategoryDTO>> getCategory() {
        ResponseEntity<List<CategoryDTO>> responseEntity = categoryService.findAll();
        return responseEntity;
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseEntity<CategoryDTO> responseEntity = categoryService.updateCategory(categoryDTO);
        return responseEntity;
    }

}
