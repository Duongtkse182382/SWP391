package com.example.demo.Entity;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID", referencedColumnName = "customerID", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "customerID")
    private int customerID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staffID", referencedColumnName = "staffID", insertable = false, updatable = false)
    private Staff staff;

    @Column(name = "staffID")
    private int staffID;

    @Column(name = "promotionPercent")
    private double promotionPercent;
    
    @Column(name="orderCode")
    private String orderCode;

    private int quantity;
    private Double total;
    private Date date;
    private boolean type;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderstatusID",referencedColumnName = "OrderstatusID", insertable = false, updatable = false)
    private OrderStatus orderStatus;
    
    @Column(name = "OrderstatusID")
    private int OrderstatusID;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseDetail> purchaseDetails;


    @OneToMany(mappedBy = "orderID", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

}




