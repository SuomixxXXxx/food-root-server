package Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dish_items")
public class DishItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private Double weight;
    private Double price;
    private Integer quantity;

    @OneToMany(mappedBy = "dishItem")
    private List<OrderContent> orderContents;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public List<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }
}
