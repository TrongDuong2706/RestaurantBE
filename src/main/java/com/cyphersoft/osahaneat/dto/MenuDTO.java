package com.cyphersoft.osahaneat.dto;

import com.cyphersoft.osahaneat.entity.Category;

public class MenuDTO {
    private int id;
    private String title;
    private String image;
    private boolean isFreeShip;

    private String description;
    private double price;
    private long totalItems;
    private String timeShip;

    private String category; // Thêm thuộc tính categoryID
    private int categoryId;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTimeShip() {
        return timeShip;
    }

    public void setTimeShip(String timeShip) {
        this.timeShip = timeShip;
    }

    // Getters and setters cho categoryID


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFreeShip() {
        return isFreeShip;
    }

    public void setFreeShip(boolean freeShip) {
        isFreeShip = freeShip;
    }
}
