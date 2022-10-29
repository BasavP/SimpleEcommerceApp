package com.basav.orderservice.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_lines")
@Getter
@Setter

//@NoArgsConstructor
@RequiredArgsConstructor
public class OrderLines {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   private  String skuCode;
   private Float price;
   private Integer quantity;

}
