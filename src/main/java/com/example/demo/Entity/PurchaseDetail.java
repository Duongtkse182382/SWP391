package com.example.demo.Entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_detail")
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderID",referencedColumnName = "orderID",insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemid", referencedColumnName = "gemID")
    private Gem gem;

    @Column(name = "gem_price")
    private float gemPrice;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialid", referencedColumnName = "materialID")
    private Material material;

    @Column(name = "weight")
    private float weight;

    @Column(name = "material_price")
    private float materialPrice;
}
