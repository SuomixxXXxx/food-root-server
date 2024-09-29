package org.chiches.foodrootservir.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Double fullPrice;
    private String status;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfCompletion;
    @NotNull(message = "Order content cannot be empty")
    private List<OrderContentDTO> orderContentDTOs;

    public OrderDTO(Double fullPrice, String status, LocalDateTime dateOfCreation, LocalDateTime dateOfCompletion, List<OrderContentDTO> orderContentDTOs) {
        this.fullPrice = fullPrice;
        this.status = status;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.orderContentDTOs = orderContentDTOs;
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

    public List<OrderContentDTO> getOrderContentDTOs() {
        return orderContentDTOs;
    }

    public void setOrderContentDTOs(List<OrderContentDTO> orderContentDTOs) {
        this.orderContentDTOs = orderContentDTOs;
    }
}
