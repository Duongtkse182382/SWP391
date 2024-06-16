package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailID;
    
    @Column(name = "orderID")
    private int orderID;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderID",referencedColumnName = "orderID",insertable = false, updatable = false)
    private Order order;
    
    private int productID;
    private float total;
    private String note;
}
