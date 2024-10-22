package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    public OrderEntity(UserEntity user, OrderStatus status, Double fullPrice, LocalDateTime dateOfCreation, List<OrderContentEntity> orderContents) {
        this.user = user;
        this.status = status;
        this.fullPrice = fullPrice;
        this.dateOfCreation = dateOfCreation;
        this.orderContents = orderContents;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(LocalDateTime dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public List<OrderContentEntity> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContentEntity> orderContents) {
        this.orderContents = orderContents;
    }
}
