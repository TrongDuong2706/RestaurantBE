package com.cyphersoft.osahaneat.payload.request;

import java.util.List;

public class CartRequest {

    private int userId;
    private int restaurantId;
    private List<CartItem > foodItems;

    public static class CartItem  {
        private int foodId;
        private int quantity;

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

        // Getters and Setters
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<CartItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<CartItem> foodItems) {
        this.foodItems = foodItems;
    }
}
