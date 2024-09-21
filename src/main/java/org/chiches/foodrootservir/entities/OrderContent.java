package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "order_contents")
public class OrderContent extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_item_id")
    private DishItem dishItem;

    private Integer quantity;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DishItem getDish() {
        return dishItem;
    }

    public void setDish(DishItem dish) {
        this.dishItem = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

