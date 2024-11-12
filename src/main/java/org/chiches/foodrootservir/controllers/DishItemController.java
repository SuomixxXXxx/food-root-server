package org.chiches.foodrootservir.controllers;

import jakarta.validation.Valid;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.services.DishItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "dishItems")
public class DishItemController {

    private final DishItemService dishItemService;

    public DishItemController(DishItemService dishItemService) {
        this.dishItemService = dishItemService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<DishItemDTO> createDishItem(@ModelAttribute DishItemDTO dishItemDTO) {
        ResponseEntity<DishItemDTO> responseEntity;
        responseEntity = dishItemService.createDishItem(dishItemDTO);
        return responseEntity;
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<DishItemDTO> getDishItem(@PathVariable("id") Long id) {
        ResponseEntity<DishItemDTO> responseEntity;
        responseEntity = dishItemService.findById(id);
        return responseEntity;
    }

    @GetMapping(path = "/getByCategory")
    public ResponseEntity<List<DishItemDTO>> getAllByCategory (@RequestParam Long categoryId) {
        ResponseEntity<List<DishItemDTO>> responseEntity;
        responseEntity = dishItemService.getAllByCategory(categoryId);
        return responseEntity;
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<DishItemDTO>> getDishItems() {
        ResponseEntity<List<DishItemDTO>> responseEntity;
        responseEntity = dishItemService.findAll();
        return responseEntity;
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<DishItemDTO> updateDishItem(@ModelAttribute DishItemDTO dishItemDTO) {
        ResponseEntity<DishItemDTO> responseEntity;
        responseEntity = dishItemService.update(dishItemDTO);
        return responseEntity;
    }

    @GetMapping(path = "/getByName")
    public ResponseEntity<List<DishItemDTO>> getAllByName(@RequestParam String name) {
        ResponseEntity<List<DishItemDTO>> responseEntity;
        responseEntity = dishItemService.getAllByName(name);
        return responseEntity;
    }

    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam Long id) {
        dishItemService.delete(id);
    }

    @PostMapping(path = "/upload-picture")
    public ResponseEntity<UrlDTO> uploadPicture(@ModelAttribute FileUploadDTO fileUploadDTO) {
        ResponseEntity<UrlDTO> responseEntity;
        responseEntity = dishItemService.uploadImage(fileUploadDTO);
        return responseEntity;
    }

}
