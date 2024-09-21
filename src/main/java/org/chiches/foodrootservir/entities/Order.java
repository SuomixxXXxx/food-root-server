package org.chiches.foodrootservir.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double fullPrice;
    private String status;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;

    @OneToMany(mappedBy = "order")
    private List<OrderContent> orderContents;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }
}
