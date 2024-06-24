package com.example.demo.Entity;

import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GemPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gemPriceListID;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "gemID", referencedColumnName = "gemID", insertable = false, updatable = false)
    private Gem gem;


    @Column(name = "gemID")
    private int gemID;

    private float buyPrice;
    private float sellPrice;
    private Date applyDate;
}
