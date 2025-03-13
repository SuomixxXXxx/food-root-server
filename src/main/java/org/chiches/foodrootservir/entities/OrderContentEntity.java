package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "order_contents")
public class OrderContentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_item_id")
    private DishItemEntity dishItem;

    private Integer quantity;

    protected OrderContentEntity() {
    }

    public OrderContentEntity(DishItemEntity dishItem, Integer quantity, OrderEntity order) {
        this.quantity = quantity;
        this.dishItem = dishItem;
        this.order = order;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public DishItemEntity getDishItem() {
        return dishItem;
    }

    public void setDishItem(DishItemEntity dishItem) {
        this.dishItem = dishItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

