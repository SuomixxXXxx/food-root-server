package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;
import org.chiches.foodrootservir.exceptions.order.NotEnoughStockException;
import org.chiches.foodrootservir.exceptions.order.OrderStatusChangeException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private Double fullPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderContentEntity> orderContents;

    protected OrderEntity() {
    }

    public OrderEntity(UserEntity user) {
        this.user = user;
        this.status = OrderStatus.CREATED;
        this.dateOfCreation = LocalDateTime.now();
        this.orderContents = new ArrayList<>();
        this.fullPrice = 0d;
    }

    public void setOrderContent(DishItemEntity dishItemEntity, int quantity) {
        if (dishItemEntity.getQuantity() < quantity) {
            throw new NotEnoughStockException("Not enough stock for " + dishItemEntity.getName());
        }
        OrderContentEntity orderContentEntity = new OrderContentEntity(
                dishItemEntity,
                quantity,
                this
        );
        orderContents.add(orderContentEntity);
        this.fullPrice += dishItemEntity.getPrice() * orderContentEntity.getQuantity();
        this.fullPrice = Math.floor(fullPrice * 100) / 100;
    }

    public void changeStatus(OrderStatus orderStatus) {
        if (status.equals(OrderStatus.CREATED)) {
            switch (orderStatus) {
                case COMPLETED -> {
                    this.dateOfCompletion = LocalDateTime.now();
                    this.status = OrderStatus.COMPLETED;
                }
                case CANCELED -> {
                    this.status = OrderStatus.CANCELED;
                }
                default -> {
                    throw new OrderStatusChangeException("Invalid order status");
                }
            }
        } else {
            throw new OrderStatusChangeException("Cannot change order status. Current status: " + status);
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public List<OrderContentEntity> getOrderContents() {
        return orderContents;
    }

}
