package com.example.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialPriceListID;

    @NotNull(message = "Vui lòng không để trống trường này")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "materialID", referencedColumnName = "materialID", insertable = false, updatable = false)
    private Material material;

    @Column(name = "materialID")
    private int materialID;

    @NotNull(message = "Vui lòng không để trống trường này")
    private float buyPrice;

    @NotNull(message = "Vui lòng không để trống trường này")
    private float sellPrice;

    private Date applyDate;

    @OneToMany(mappedBy = "materialPriceList")
    private List<Product> products;
    }