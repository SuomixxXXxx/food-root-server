package org.chiches.foodrootservir.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.chiches.foodrootservir.entities.CategoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DishItemDTO {
    @NotNull(message = "Id cannot be empty")
    private Long id;
    @NotNull(message = "Category cannot be empty")
    private CategoryDTO categoryDTO;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Weight cannot be empty")
    //min max не рабатают с double и float
    private Double weight;
    @NotNull(message = "Price cannot be empty")
    private Double price;
    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Max(value = 20, message = "Quantity must be less than 20")
    private Integer quantity;
    @NotNull(message = "OrderContentDTO cannot be empty")
    private List<OrderContentDTO> orderContentDTOs;
    private MultipartFile multipartFile;
    private String url;

    public DishItemDTO() {
    }

    public DishItemDTO(Long id, CategoryDTO categoryDTO, String name, Double weight, Double price, Integer quantity, List<OrderContentDTO> orderContentDTOs) {
        this.id = id;
        this.categoryDTO = categoryDTO;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.orderContentDTOs = orderContentDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<OrderContentDTO> getOrderContentDTOs() {
        return orderContentDTOs;
    }

    public void setOrderContentDTOs(List<OrderContentDTO> orderContentDTOs) {
        this.orderContentDTOs = orderContentDTOs;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
