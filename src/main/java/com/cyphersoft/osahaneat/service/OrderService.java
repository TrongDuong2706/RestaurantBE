package com.cyphersoft.osahaneat.service;

import com.cyphersoft.osahaneat.dto.OrderItemDTO;
import com.cyphersoft.osahaneat.entity.*;
import com.cyphersoft.osahaneat.entity.keys.KeyOrderItem;
import com.cyphersoft.osahaneat.payload.request.CartRequest;
import com.cyphersoft.osahaneat.payload.request.OrderRequest;
import com.cyphersoft.osahaneat.repository.CartRepository;
import com.cyphersoft.osahaneat.repository.FoodRepository;
import com.cyphersoft.osahaneat.repository.OrderItemRepository;
import com.cyphersoft.osahaneat.repository.OrderRepository;
import com.cyphersoft.osahaneat.service.imp.OrderServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService implements OrderServiceImp {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository; // Assume you have a FoodRepository
    @Override
    public boolean insertOrder(OrderRequest orderRequest) {
        try{

            Users users = new Users();
            users.setId(orderRequest.getUserId());

            Restaurant restaurant = new Restaurant();
            restaurant.setId(orderRequest.getRestaurantId());

            Orders orders = new Orders();
            orders.setUsers(users);
            orders.setRestaurant(restaurant);
            orderRepository.save(orders);

            List<OrderItem> items = new ArrayList<>();

            for(int idFood: orderRequest.getFoodIds()){

                Food food = new Food();
                food.setId(idFood);

                OrderItem orderItem = new OrderItem();
                KeyOrderItem keyOrderItem =new KeyOrderItem(orders.getId(), idFood);
                orderItem.setKeyOrderItem(keyOrderItem);
                items.add(orderItem);
            }
            orderItemRepository.saveAll(items);
            return true;
        }
        catch(Exception e){
            System.out.println("Error Insert Order "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            Integer orderIdValue = orderItem.getKeyOrderItem().getOrderId();
            Integer foodIdValue = orderItem.getKeyOrderItem().getFoodId();
            Integer quantityValue = orderItem.getQuantity();
            Date createDateValue = orderItem.getCreateDate();

            // Kiểm tra và xử lý giá trị null
            if (orderIdValue == null) {
                orderIdValue = 0; // hoặc giá trị mặc định khác
            }
            if (foodIdValue == null) {
                foodIdValue = 0; // hoặc giá trị mặc định khác
            }
            if (quantityValue == null) {
                quantityValue = 0; // hoặc giá trị mặc định khác
            }
            if (createDateValue == null) {
                createDateValue = new Date(); // hoặc giá trị mặc định khác
            }

            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    orderIdValue,
                    foodIdValue,
                    quantityValue,
                    createDateValue
            );
            orderItemDTOs.add(orderItemDTO);
        }

        return orderItemDTOs;
    }


    @Override
    public boolean addToCart(CartRequest cartRequest) {
        try {
            Users user = new Users();
            user.setId(cartRequest.getUserId());
            

            Restaurant restaurant = new Restaurant();
            restaurant.setId(cartRequest.getRestaurantId());

            Cart cart = cartRepository.findByUser(user);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cartRepository.save(cart);
            }

            Orders order = orderRepository.findFirstByUsersAndRestaurantAndStatus(user, restaurant, "Pending");
            if (order == null) {
                order = new Orders();
                order.setUsers(user);
                order.setRestaurant(restaurant);
                order.setStatus("Pending");
                order.setCreateDate(new Date());
                orderRepository.save(order);
            }

            cart.setOrder(order);
            cartRepository.save(cart);

            for (CartRequest.CartItem cartItem : cartRequest.getFoodItems()) {
                Food food = new Food();
                food.setId(cartItem.getFoodId());

                OrderItem orderItem = orderItemRepository.findByCartAndFood(cart, food);
                if (orderItem == null) {
                    orderItem = new OrderItem();
                    KeyOrderItem keyOrderItem = new KeyOrderItem(order.getId(), food.getId());
                    orderItem.setKeyOrderItem(keyOrderItem);
                    orderItem.setOrders(order);
                    orderItem.setFood(food);
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setCreateDate(new Date());
                    orderItem.setCart(cart);
                } else {
                    orderItem.setQuantity(orderItem.getQuantity() + cartItem.getQuantity());
                }
                orderItemRepository.save(orderItem); // Lưu hoặc cập nhật OrderItem
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error adding to cart: " + e.getMessage());
            return false;
        }
    }


    @Transactional
    public boolean checkoutCart(int userId) {
        try {
            Users user = new Users();
            user.setId(userId);

            Cart cart = cartRepository.findByUser(user);
            if (cart == null || cart.getOrderItems().isEmpty()) {
                throw new Exception("Cart is empty");
            }

            Orders order = cart.getOrder();
            order.setStatus("Completed");
            orderRepository.save(order); // Cập nhật trạng thái đơn hàng thành Completed

            // Xóa cartId của từng OrderItem trong Cart
            for (OrderItem orderItem : cart.getOrderItems()) {
                orderItem.setCart(null);
            }

            cartRepository.save(cart); // Lưu lại Cart để cập nhật thay đổi

            return true;
        } catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
            return false;
        }
    }


}
