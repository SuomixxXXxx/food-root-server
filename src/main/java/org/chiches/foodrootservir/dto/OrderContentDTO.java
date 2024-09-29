package org.chiches.foodrootservir.dto;

public class OrderContentDTO {
    private OrderDTO orderDTO;
    private DishItemDTO dishItemDTO;
    private Integer quantity;

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
