package com.basav.orderservice.service;

import com.basav.orderservice.dto.InventoryResponse;
import com.basav.orderservice.dto.OrderLinesDTO;
import com.basav.orderservice.dto.OrderRequestDTO;
import com.basav.orderservice.model.Order;
import com.basav.orderservice.model.OrderLines;
import com.basav.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClientBuilder;;
    public void placeOrder(OrderRequestDTO orderRequestDTO) throws Exception {



        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        List<OrderLines> orderLines = orderRequestDTO.getOrderLinesDTOS().stream()
                .map(this::mapToDTO).collect(Collectors.toList());

        order.setOrderLines(orderLines);

        /*Fetch all the sku codes of the order lines*/
        List<String> skuCodes = orderLines.stream()
                .map(OrderLines::getSkuCode)
                .collect(Collectors.toList());

        /*Make a call to the inventory service for all skuCodes*/
        InventoryResponse[] InventoryCheck;
        InventoryCheck = webClientBuilder
                .get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> {
                                log.info("uri : "+(uriBuilder.queryParam("skuCode", skuCodes).build()).toString());
                            return uriBuilder.queryParam("skuCode", skuCodes).build();
                        })
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        /*Check if all the skuCodes returned from the inventory service call have inventory*/
        boolean allInStock = orderLines.size() == InventoryCheck.length;


        log.info("allInStock : "+allInStock);

        /*if condition here  : if all the ordered items have inventory only then place the order else throw error */
        if(allInStock){

            orderRepository.save(order);

        }else{
            throw new Exception("Product not in stock");
        }



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
