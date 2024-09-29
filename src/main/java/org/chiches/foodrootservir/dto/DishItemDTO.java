package org.chiches.foodrootservir.dto;

import java.util.List;

public class DishItemDTO {
    private Long id;
    private CategoryDTO categoryDTO;
    private String name;
    private Double weight;
    private Double price;
    private Integer quantity;
    private List<OrderContentDTO> orderContentDTOs;

    public DishItemDTO(Long id, CategoryDTO categoryDTO, String name, Double weight, Double price, Integer quantity, List<OrderContentDTO> orderContentDTOs) {
        this.id = id;
        this.categoryDTO = categoryDTO;
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

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
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
