package com.coffee_shop.models.binding;

import com.coffee_shop.models.entities.CategoryEntity;
import com.coffee_shop.models.entities.CategoryNameEnum;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderAddBM {

    private String name;
    private BigDecimal price;
    private LocalDateTime orderTime;
    private String description;
    private CategoryNameEnum category;

    public OrderAddBM() {
    }

    @NotNull
    @Length(min = 3, max = 20, message = "Name must be between 3 and 20")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Positive(message = "The price must be positive")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @PastOrPresent(message = "Order time cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    @NotBlank
    @Size(min = 5, message = "Description size must be minimum 5 char")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "You must select category")
    public CategoryNameEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryNameEnum category) {
        this.category = category;
    }
}
