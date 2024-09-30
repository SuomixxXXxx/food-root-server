package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.chiches.foodrootservir.entities.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Double fullPrice;
    private OrderStatus status;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;
    @NotNull(message = "Order content cannot be empty")
    private List<OrderContentDTO> orderContentDTOs;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Double fullPrice, OrderStatus status, LocalDateTime dateOfCreation, LocalDateTime dateOfCompletion, List<OrderContentDTO> orderContentDTOs) {
        this.id = id;
        this.fullPrice = fullPrice;
        this.status = status;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.orderContentDTOs = orderContentDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderContentDTO> getOrderContentDTOs() {
        return orderContentDTOs;
    }

    public void setOrderContentDTOs(List<OrderContentDTO> orderContentDTOs) {
        this.orderContentDTOs = orderContentDTOs;
    }
}
