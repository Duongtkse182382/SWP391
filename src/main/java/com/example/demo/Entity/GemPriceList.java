package com.example.demo.Entity;

import javax.persistence.*;
import lombok.*;
import java.util.Date;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GemPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer gemPriceListID;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "gemID", referencedColumnName = "gemID", insertable = false, updatable = false)
    Gem gem;


    @Column(name = "gemID")
    int gemID;

    float buyPrice;
    float sellPrice;
    Date applyDate;
}
