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

    public DishItem getDishItem() {
        return dishItem;
    }

    public void setDishItem(DishItem dishItem) {
        this.dishItem = dishItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

