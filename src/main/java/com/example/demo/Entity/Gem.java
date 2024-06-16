package com.example.demo.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gemID;
    
    @NotNull
    private String gemCode;
    private String gemName;
    private String origin;
    private float carat;
    private String color;
    private String clarity;
    private String cut;

    @OneToMany(mappedBy = "gem")
    private List<GemPriceList> gemPriceLists;
}
