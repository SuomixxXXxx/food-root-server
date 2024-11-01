package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.dto.CategoryDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.repositories.CategoryRepository;
import org.chiches.foodrootservir.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        try {
            CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
            CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
            CategoryDTO savedDTO = modelMapper.map(savedEntity, CategoryDTO.class);
            ResponseEntity<CategoryDTO> responseEntity = ResponseEntity.ok().body(savedDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Category was not created due to problems connecting to the database");
        }
    }

    @Override
    public ResponseEntity<CategoryDTO> findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
        ResponseEntity<CategoryDTO> responseEntity = ResponseEntity.ok().body(categoryDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> findAll(boolean active) {
        List<CategoryEntity> categoryEntities;
        if (active) {
            categoryEntities = categoryRepository.findAllNotDeleted();

        } else {
            categoryEntities = categoryRepository.findAll();
        }
        List<CategoryDTO> categoryDTOs = categoryEntities.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .toList();
        ResponseEntity<List<CategoryDTO>> responseEntity = ResponseEntity.ok().body(categoryDTOs);
        return responseEntity;
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        try {
            categoryEntity.setName(categoryDTO.getName());
            CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
            CategoryDTO savedDTO = modelMapper.map(savedEntity, CategoryDTO.class);
            ResponseEntity<CategoryDTO> responseEntity = ResponseEntity.ok().body(savedDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Category was not created due to problems connecting to the database");
        }
    }

}
