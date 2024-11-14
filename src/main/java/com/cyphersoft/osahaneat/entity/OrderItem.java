package com.cyphersoft.osahaneat.entity;

import com.cyphersoft.osahaneat.entity.keys.KeyOrderItem;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name="orderitem")
public class OrderItem {
    @EmbeddedId
    KeyOrderItem keyOrderItem;

    @ManyToOne
    @JoinColumn(name="order_id", insertable = false, updatable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name="food_id", insertable = false, updatable = false)
    private Food food;

    @Column(name="quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable = true)
    private Cart cart;


    @Column(name="create_date")
    private Date createDate;


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public KeyOrderItem getKeyOrderItem() {
        return keyOrderItem;
    }

    public void setKeyOrderItem(KeyOrderItem keyOrderItem) {
        this.keyOrderItem = keyOrderItem;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
