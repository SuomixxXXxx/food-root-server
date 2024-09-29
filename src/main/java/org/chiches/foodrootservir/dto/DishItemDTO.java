package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.chiches.foodrootservir.entities.CategoryEntity;

import java.util.List;

public class DishItemDTO {
    @NotNull(message = "Id cannot be empty")
    private Long id;
    @NotNull(message = "Category cannot be empty")
    private CategoryDTO category;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Weight cannot be empty")
    //min max не рабатают с double и float
    private Double weight;
    @NotNull(message = "Price cannot be empty")
    private Double price;
    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 20, message = "Quantity must be less than 20")
    private Integer quantity;
    @NotNull(message = "OrderContentDTO cannot be empty")
    private List<OrderContentDTO> orderContentDTOs;

    public DishItemDTO(Long id, CategoryDTO category, String name, Double weight, Double price, Integer quantity, List<OrderContentDTO> orderContentDTOs) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.orderContentDTOs = orderContentDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
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
