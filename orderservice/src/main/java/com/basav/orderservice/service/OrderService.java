package com.basav.orderservice.service;

import com.basav.orderservice.dto.OrderLinesDTO;
import com.basav.orderservice.dto.OrderRequestDTO;
import com.basav.orderservice.model.Order;
import com.basav.orderservice.model.OrderLines;
import com.basav.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDTO orderRequestDTO) {

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        List<OrderLines> orderLines = orderRequestDTO.getOrderLinesDTOS().stream()
                .map(this::mapToDTO).collect(Collectors.toList());



        order.setOrderLines(orderLines);

        orderRepository.save(order);
    }

    private OrderLines mapToDTO(OrderLinesDTO orderLinesDTO) {

        OrderLines orderLines= new OrderLines();

        orderLines.setId(orderLinesDTO.getId());
        orderLines.setSkuCode(orderLinesDTO.getSkuCode());
        orderLines.setPrice(orderLinesDTO.getPrice());
        orderLines.setQuantity(orderLinesDTO.getQuantity());

        return  orderLines;
    }
}
