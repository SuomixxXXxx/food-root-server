package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.repositories.CategoryRepository;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.services.DishItemService;
import org.chiches.foodrootservir.services.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishItemServiceImpl implements DishItemService {

    private final DishItemRepository dishItemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public DishItemServiceImpl(DishItemRepository dishItemRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, StorageService storageService) {
        this.dishItemRepository = dishItemRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    @Override
    public ResponseEntity<DishItemDTO> createDishItem(DishItemDTO dishItemDTO) {
        DishItemEntity dishItemEntity = modelMapper.map(dishItemDTO, DishItemEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findById(dishItemDTO.getCategoryDTO().getId())
                .orElseThrow();
        dishItemEntity.setCategory(categoryEntity);
        try {
            DishItemEntity savedDishItemEntity = dishItemRepository.save(dishItemEntity);
            DishItemDTO savedDishItemDTO = modelMapper.map(savedDishItemEntity, DishItemDTO.class);
            MultipartFile file = dishItemDTO.getFile();
            String name = String.format("dishes/%d.jpg", dishItemEntity.getId());
            if (file != null) {
                String url = storageService.uploadFile(file, name);
                savedDishItemDTO.setUrl(url);
            }
            ResponseEntity<DishItemDTO> responseEntity = ResponseEntity.ok().body(savedDishItemDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Dish item was not created due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<DishItemDTO> findById(Long id) {
        DishItemEntity dishItemEntity = dishItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish item with id " + id + " not found"));
        DishItemDTO dishItemDTO = modelMapper.map(dishItemEntity, DishItemDTO.class);
        ResponseEntity<DishItemDTO> responseEntity = ResponseEntity.ok().body(dishItemDTO);
        return responseEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<List<DishItemDTO>> findAll() {
        List<DishItemEntity> dishItemEntities = dishItemRepository.findAll();
        List<DishItemDTO> dishItemDTOs = dishItemEntities.stream()
                .map(dishItemEntity -> modelMapper.map(dishItemEntity, DishItemDTO.class))
                .toList();
        ResponseEntity<List<DishItemDTO>> responseEntity = ResponseEntity.ok().body(dishItemDTOs);
        return responseEntity;
    }

    @Override
    public ResponseEntity<DishItemDTO> update(DishItemDTO dishItemDTO) {
        DishItemEntity dishItemEntity = dishItemRepository.findById(dishItemDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Dish item not found"));
        if (dishItemDTO.getCategoryDTO() != null && dishItemDTO.getCategoryDTO().getId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(dishItemDTO.getCategoryDTO().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            dishItemEntity.setCategory(categoryEntity);
        }
        if (dishItemDTO.getName() != null) {
            dishItemEntity.setName(dishItemDTO.getName());
        }
        if (dishItemDTO.getPrice() != null) {
            dishItemEntity.setPrice(dishItemDTO.getPrice());
        }
        if (dishItemDTO.getQuantity() != null) {
            dishItemEntity.setQuantity(dishItemDTO.getQuantity());
        }
        if (dishItemDTO.getWeight() != null) {
            dishItemEntity.setWeight(dishItemDTO.getWeight());
        }
        try {
            DishItemEntity savedDishItemEntity = dishItemRepository.save(dishItemEntity);
            DishItemDTO savedDishItemDTO = modelMapper.map(savedDishItemEntity, DishItemDTO.class);
            MultipartFile file = dishItemDTO.getFile();
            String name = String.format("dishes/%d.jpg", dishItemEntity.getId());
            if (file != null) {
                String url = storageService.uploadFile(file, name);
                savedDishItemDTO.setUrl(url);
            }
            ResponseEntity<DishItemDTO> responseEntity = ResponseEntity.ok().body(savedDishItemDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Dish item was not updated due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<List<DishItemDTO>> getAllByCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
        List<DishItemEntity> dishItemEntities = dishItemRepository.findAllByCategoryAndIsDeletedFalse(categoryEntity);
        List<DishItemDTO> dishItemDTOs = dishItemEntities.stream()
                .map(dishItemEntity -> modelMapper.map(dishItemEntity, DishItemDTO.class))
                .toList();
        ResponseEntity<List<DishItemDTO>> responseEntity = ResponseEntity.ok().body(dishItemDTOs);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<DishItemDTO>> getAllByName(String name) {
        List<DishItemDTO> dishItemDTOs = new ArrayList<>();
        if (!name.isBlank()) {
            List<DishItemEntity> dishItemEntities = dishItemRepository.findAllByNameContainingIgnoreCaseAndIsDeletedFalse(name.strip());
            dishItemDTOs = dishItemEntities.stream()
                    .map(dishItemEntity -> modelMapper.map(dishItemEntity, DishItemDTO.class))
                    .toList();
        }
        ResponseEntity<List<DishItemDTO>> responseEntity = ResponseEntity.ok().body(dishItemDTOs);
        return responseEntity;
    }

    @Override
    public void delete(Long id) {
        DishItemEntity dishItemEntity = dishItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish item with id " + id + " not found"));
        dishItemEntity.setDeleted(true);
        try {
            dishItemRepository.save(dishItemEntity);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Dish item was not deleted due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<UrlDTO> uploadImage(FileUploadDTO fileUploadDTO) {
        MultipartFile file = fileUploadDTO.getFile();
        String url;
        String name = String.format("dishes/%d.jpg", fileUploadDTO.getId());
            if (file != null) {
                url = storageService.uploadFile(file, name);
                UrlDTO urlDTO = new UrlDTO(url);
                ResponseEntity<UrlDTO> responseEntity = ResponseEntity.ok().body(urlDTO);
                return responseEntity;
            } else {
                throw new RuntimeException();
        }
    }

}
