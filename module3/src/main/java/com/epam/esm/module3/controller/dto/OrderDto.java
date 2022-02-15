package com.epam.esm.module3.controller.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import java.util.Objects;

public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;
    private Double price;
    private String purchaseDate;
    @Min(value = 1)
    private Long userId;
    @Min(value = 1)
    private Long certificateId;

    public OrderDto() {
    }

    public OrderDto(Long id, Double price, String purchaseDate, Long userId, Long certificateId) {
        this.id = id;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.userId = userId;
        this.certificateId = certificateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDto)) return false;
        if (!super.equals(o)) return false;

        OrderDto orderDto = (OrderDto) o;

        if (!Objects.equals(id, orderDto.id)) return false;
        if (!Objects.equals(price, orderDto.price)) return false;
        if (!Objects.equals(purchaseDate, orderDto.purchaseDate))
            return false;
        if (!Objects.equals(userId, orderDto.userId)) return false;
        return certificateId != null ? certificateId.equals(orderDto.certificateId) : orderDto.certificateId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (certificateId != null ? certificateId.hashCode() : 0);
        return result;
    }
}
