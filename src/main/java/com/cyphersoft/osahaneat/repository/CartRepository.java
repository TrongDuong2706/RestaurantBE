package com.cyphersoft.osahaneat.repository;


import com.cyphersoft.osahaneat.entity.Cart;
import com.cyphersoft.osahaneat.entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(Users user);
    Optional<Cart> findByUserId(int userId);



}
