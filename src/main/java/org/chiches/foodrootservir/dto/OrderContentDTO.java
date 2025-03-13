package org.chiches.foodrootservir.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderContentDTO {
    @NotNull(message = "Order cannot be empty")
    private OrderDTO orderDTO;
    @NotNull(message = "Dish item cannot be empty")
    private DishItemDTO dishItemDTO;
    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 10, message = "Quantity must be less than 10")
    private Integer quantity;

    public OrderContentDTO() {
    }

    public OrderContentDTO(OrderDTO orderDTO, DishItemDTO dishItemDTO, Integer quantity) {
        this.orderDTO = orderDTO;
        this.dishItemDTO = dishItemDTO;
        this.quantity = quantity;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public DishItemDTO getDishItemDTO() {
        return dishItemDTO;
    }

    public void setDishItemDTO(DishItemDTO dishItemDTO) {
        this.dishItemDTO = dishItemDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
