package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class DishIdsDTO {
    @NotNull(message = "dish ids list cannot be null")
    @NotEmpty(message = "dish ids list cannot be empty")
    private Set<@NotNull(message = "id cannot be null") Long> dishIds;

    public Set<Long> getDishIds() {
        return dishIds;
    }

    public void setDishIds(Set<Long> dishIds) {
        this.dishIds = dishIds;
    }
}
