package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dish_items")
public class DishItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String name;
    private Double weight;
    private Double price;
    private Integer quantity;

    @OneToMany(mappedBy = "dishItem", fetch = FetchType.LAZY)
    private List<OrderContentEntity> orderContents;

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

    public List<OrderContentEntity> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContentEntity> orderContents) {
        this.orderContents = orderContents;
    }
}