package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.dto.FileUploadDTO;
import org.chiches.foodrootservir.dto.UrlDTO;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.repositories.CategoryRepository;
import org.chiches.foodrootservir.services.CategoryService;
import org.chiches.foodrootservir.services.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper, StorageService storageService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
            CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
            CategoryDTO savedDTO = modelMapper.map(savedEntity, CategoryDTO.class);
            MultipartFile file = categoryDTO.getFile();
            String name = String.format("dishes/%d.jpg", categoryEntity.getId());
            if (file != null) {
                String url = storageService.uploadFile(file, name);
                savedDTO.setUrl(url);
            }
            return savedDTO;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Category was not created due to problems connecting to the database");
        }
    }

    @Override
    @Cacheable("category")
    public CategoryDTO findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    @Cacheable("categories")
    public List<CategoryDTO> findAll(boolean active) {
        List<CategoryEntity> categoryEntities;
        if (active) {
            categoryEntities = categoryRepository.findAllNotDeleted();

        } else {
            categoryEntities = categoryRepository.findAll();
        }
        List<CategoryDTO> categoryDTOs = categoryEntities.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .toList();
        return categoryDTOs;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#categoryDTO.id"),
            @CacheEvict(value = "categories", allEntries = true)
    })
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        try {
            categoryEntity.setName(categoryDTO.getName());
            CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
            CategoryDTO savedDTO = modelMapper.map(savedEntity, CategoryDTO.class);
            MultipartFile file = categoryDTO.getFile();
            String name = String.format("dishes/%d.jpg", categoryEntity.getId());
            if (file != null) {
                String url = storageService.uploadFile(file, name);
                savedDTO.setUrl(url);
            }
            return savedDTO;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Category was not created due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<UrlDTO> uploadImage(FileUploadDTO fileUploadDTO) {
        MultipartFile file = fileUploadDTO.getFile();
        String url;
        String name = String.format("categories/%d.jpg", fileUploadDTO.getId());
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
