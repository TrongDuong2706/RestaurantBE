package com.cyphersoft.osahaneat.controller;

import com.cyphersoft.osahaneat.dto.OrderItemDTO;
import com.cyphersoft.osahaneat.payload.ResponseData;
import com.cyphersoft.osahaneat.payload.request.CartRequest;
import com.cyphersoft.osahaneat.payload.request.OrderRequest;
import com.cyphersoft.osahaneat.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderServiceImp orderServiceImp;



    @PostMapping()
    public ResponseEntity<?> inserOrder(@RequestBody OrderRequest orderRequest){

        return new ResponseEntity<>(orderServiceImp.insertOrder(orderRequest), HttpStatus.OK);
    }


    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        boolean isAdded = orderServiceImp.addToCart(cartRequest);
        if (isAdded) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/order-items/{orderId}")
    public ResponseEntity<ResponseData> getOrderItems(@PathVariable int orderId) {
        ResponseData responseData = new ResponseData();
        try {
            List<OrderItemDTO> orderItems = orderServiceImp.getOrderItemsByOrderId(orderId);

            responseData.setStatus(HttpStatus.OK.value());
            responseData.setSuccess(true);
            responseData.setDesc("Order items fetched successfully");
            responseData.setData(orderItems);

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Failed to fetch order items: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @PostMapping("/checkoutCart/{orderId}")
    public ResponseEntity<ResponseData> checkoutCart(@PathVariable int orderId) {
        ResponseData responseData = new ResponseData();
        try {
            boolean success = orderServiceImp.checkoutCart(orderId);

            if (success) {
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setSuccess(true);
                responseData.setDesc("Cart checked out successfully");
            } else {
                responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseData.setSuccess(false);
                responseData.setDesc("Failed to check out cart");
            }

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Failed to check out cart: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

}
