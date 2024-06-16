package com.example.demo.Entity;

import jakarta.persistence.*;
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
    private int gemPriceListID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gemID", referencedColumnName = "gemID", insertable = false, updatable = false)
    private Gem gem;

    @Column(name = "gemID")
    private int gemID;

    private float buyPrice;
    private float sellPrice;
    private Date applyDate;
}
