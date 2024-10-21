package org.chiches.foodrootservir.config;

import org.chiches.foodrootservir.dto.DishItemDTO;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(DishItemEntity.class, DishItemDTO.class)
                //FIXME: causes bug that he cant retrieve category name, dunno so here we are
                .addMappings(mapper -> mapper.map(DishItemEntity::getCategory, DishItemDTO::setCategoryDTO))
                .addMappings(mapper -> mapper.skip(DishItemDTO::setOrderContentDTOs));
        
        return modelMapper;
    }
}
