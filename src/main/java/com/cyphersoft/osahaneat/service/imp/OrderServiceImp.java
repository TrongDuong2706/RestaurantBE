package com.cyphersoft.osahaneat.service.imp;

import com.cyphersoft.osahaneat.dto.OrderItemDTO;
import com.cyphersoft.osahaneat.payload.request.CartRequest;
import com.cyphersoft.osahaneat.payload.request.OrderRequest;

import java.util.List;

public interface OrderServiceImp {
    boolean insertOrder(OrderRequest orderRequest);


    List<OrderItemDTO> getOrderItemsByOrderId(int orderId);
    boolean addToCart(CartRequest cartRequest);

    boolean checkoutCart(int orderId);


}
