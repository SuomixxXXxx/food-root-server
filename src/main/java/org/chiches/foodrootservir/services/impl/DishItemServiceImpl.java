package org.chiches.foodrootservir.services.impl;

import jakarta.persistence.PersistenceException;
import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.exceptions.DatabaseException;
import org.chiches.foodrootservir.exceptions.ResourceNotFoundException;
import org.chiches.foodrootservir.repositories.CategoryRepository;
import org.chiches.foodrootservir.repositories.DishItemRepository;
import org.chiches.foodrootservir.services.DishItemService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishItemServiceImpl implements DishItemService {

    private final DishItemRepository dishItemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public DishItemServiceImpl(DishItemRepository dishItemRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.dishItemRepository = dishItemRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<DishItemDTO> createDishItem(DishItemDTO dishItemDTO) {
        DishItemEntity dishItemEntity = modelMapper.map(dishItemDTO, DishItemEntity.class);
        try {
            DishItemEntity savedDishItemEntity = dishItemRepository.save(dishItemEntity);
            DishItemDTO savedDishItemDTO = modelMapper.map(savedDishItemEntity, DishItemDTO.class);
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
        modelMapper.typeMap(DishItemEntity.class, DishItemDTO.class).addMappings(mapper -> {
            mapper.skip(DishItemDTO::setOrderContentDTOs);
        });
        DishItemDTO dishItemDTO = modelMapper.map(dishItemEntity, DishItemDTO.class);
        ResponseEntity<DishItemDTO> responseEntity = ResponseEntity.ok().body(dishItemDTO);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<DishItemDTO>> findAll() {
        List<DishItemEntity> dishItemEntities = dishItemRepository.findAll();
        modelMapper.typeMap(DishItemEntity.class, DishItemDTO.class).addMappings(mapper -> {
            mapper.skip(DishItemDTO::setOrderContentDTOs);
        });
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
        CategoryEntity categoryEntity = categoryRepository.findById(dishItemDTO.getCategoryDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        dishItemEntity.setCategory(categoryEntity);
        dishItemEntity.setName(dishItemDTO.getName());
        dishItemEntity.setPrice(dishItemDTO.getPrice());
        dishItemEntity.setQuantity(dishItemDTO.getQuantity());
        dishItemEntity.setWeight(dishItemDTO.getWeight());
        try {
            DishItemEntity savedDishItemEntity = dishItemRepository.save(dishItemEntity);
            DishItemDTO savedDishItemDTO = modelMapper.map(savedDishItemEntity, DishItemDTO.class);
            ResponseEntity<DishItemDTO> responseEntity = ResponseEntity.ok().body(savedDishItemDTO);
            return responseEntity;
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseException("Category was not created due to problems connecting to the database");
        }
    }

}
