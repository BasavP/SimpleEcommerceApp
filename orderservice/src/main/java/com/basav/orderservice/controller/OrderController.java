package com.basav.orderservice.controller;


import com.basav.orderservice.dto.OrderRequestDTO;
import com.basav.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws Exception {

        orderService.placeOrder(orderRequestDTO);
        return "Order created successfully";
    }
}
