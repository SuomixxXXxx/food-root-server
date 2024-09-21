package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "category")
    private List<DishItem> dishItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DishItem> getDishItems() {
        return dishItems;
    }

    public void setDishItems(List<DishItem> dishItems) {
        this.dishItems = dishItems;
    }
}

