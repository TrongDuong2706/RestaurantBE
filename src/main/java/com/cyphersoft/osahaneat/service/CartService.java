package com.cyphersoft.osahaneat.service;

import com.cyphersoft.osahaneat.dto.CartDTO;
import com.cyphersoft.osahaneat.entity.Cart;
import com.cyphersoft.osahaneat.entity.OrderItem;
import com.cyphersoft.osahaneat.entity.Users;
import com.cyphersoft.osahaneat.repository.CartRepository;
import com.cyphersoft.osahaneat.repository.OrderItemRepository;
import com.cyphersoft.osahaneat.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements CartServiceImp {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public CartDTO getCartByUserId(int userId) {
        Users user = new Users();
        user.setId(userId);

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            return null; // Or throw an exception if you prefer
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setUserId(userId);

        List<CartDTO.CartItemDTO> cartItems = new ArrayList<>();
        for (OrderItem orderItem : cart.getOrderItems()) {
            CartDTO.CartItemDTO cartItemDTO = new CartDTO.CartItemDTO();
            cartItemDTO.setFoodId(orderItem.getFood().getId());
            cartItemDTO.setFoodName(orderItem.getFood().getTitle()); // Assuming Food has a getName() method
            cartItemDTO.setPrice(orderItem.getFood().getPrice());
            cartItemDTO.setAvatar(orderItem.getFood().getImage());
            cartItemDTO.setQuantity(orderItem.getQuantity());
            cartItemDTO.setCreateDate(orderItem.getCreateDate());
            cartItems.add(cartItemDTO);
        }
        cartDTO.setItems(cartItems);

        return cartDTO;
    }



}
