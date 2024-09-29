package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.NotNull;

public class CategoryDTO {
    @NotNull(message = "Id cannot be empty")
    private Long id;
    @NotNull(message = "Name cannot be empty")
    private String name;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
