package com.cyphersoft.osahaneat.dto;

import java.util.Date;

public class OrderItemDTO {

    private int orderId;
    private int foodId;
    private int quantity;
    private Date createDate;

    public OrderItemDTO(int orderId, int foodId, int quantity, Date createDate) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
        this.createDate = createDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
