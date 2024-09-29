package org.chiches.foodrootservir.dto;

import org.chiches.foodrootservir.entities.CategoryEntity;

import java.util.List;

public class DishItemDTO {
    private CategoryEntity category;
    private String name;
    private Double weight;
    private Double price;
    private Integer quantity;
    private List<OrderContentDTO> orderContentDTOs;

    public DishItemDTO(CategoryEntity category, String name, Double weight, Double price, Integer quantity, List<OrderContentDTO> orderContentDTOs) {
        this.category = category;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.orderContentDTOs = orderContentDTOs;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<OrderContentDTO> getOrderContentDTOs() {
        return orderContentDTOs;
    }

    public void setOrderContentDTOs(List<OrderContentDTO> orderContentDTOs) {
        this.orderContentDTOs = orderContentDTOs;
    }
}
