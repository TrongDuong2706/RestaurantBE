package com.cyphersoft.osahaneat.repository;

import com.cyphersoft.osahaneat.entity.Cart;
import com.cyphersoft.osahaneat.entity.Food;
import com.cyphersoft.osahaneat.entity.OrderItem;
import com.cyphersoft.osahaneat.entity.Orders;
import com.cyphersoft.osahaneat.entity.keys.KeyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, KeyOrderItem> {

    Optional<OrderItem> findByOrders_IdAndFood_Id(int orderId, int foodId);

    @Query("SELECT oi FROM orderitem oi WHERE oi.keyOrderItem.orderId = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") int orderId);

    OrderItem findByOrdersAndFood(Orders orders, Food food);

    List<OrderItem> findByOrdersId(int orderId);


    OrderItem findByCartAndFood(Cart cart, Food food);





}
