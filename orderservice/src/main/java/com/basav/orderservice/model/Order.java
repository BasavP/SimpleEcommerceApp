package com.basav.orderservice.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_header")
@Getter
@Setter

@RequiredArgsConstructor
//@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String orderNo;
    @OneToMany(cascade =  CascadeType.ALL)
    private List<OrderLines> orderLines;



}
