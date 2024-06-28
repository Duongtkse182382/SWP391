package com.example.demo.Entity;

import java.util.List;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    private String productName;
    private String productCode;
    private float weight;
    private boolean isActive;
    private float priceRate;
    private String image;
    private String orderType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID", insertable = false, updatable = false)
    private Category category;

    @Column(name = "categoryID")
    private int categoryID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gemPriceListID", referencedColumnName = "gemPriceListID", insertable = false, updatable = false)
    private GemPriceList gemPriceList;


    @Column(name = "gemPriceListID")
    private Integer gemPriceListID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeID", referencedColumnName = "typeID", insertable = false, updatable = false)
    private Type type;

    @Column(name = "typeID")
    private int typeID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materialPriceListID", referencedColumnName = "materialPriceListID", insertable = false, updatable = false)
    private MaterialPriceList materialPriceList;

    @Column(name = "materialPriceListID")
    private int materialPriceListID;
    
    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

}
