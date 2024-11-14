package com.cyphersoft.osahaneat.repository;

import com.cyphersoft.osahaneat.entity.Orders;
import com.cyphersoft.osahaneat.entity.Restaurant;
import com.cyphersoft.osahaneat.entity.Users;
import com.cyphersoft.osahaneat.payload.request.CartRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    Optional<Orders> findOrderByUsers_IdAndStatus(int id, String status);

    Orders findFirstByUsersAndRestaurantAndStatus(Users users, Restaurant restaurant, String status);
    Orders findByUsersAndRestaurantAndStatus(Users users, Restaurant restaurant, String status);




}
