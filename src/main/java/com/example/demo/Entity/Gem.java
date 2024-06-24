package com.example.demo.Entity;

import java.util.List;

import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gemID;
    private String gemCode;
    private String gemName;
    private String origin;
    private float carat;
    private String color;             
    private String clarity;
    private String cut;

    @OneToMany(mappedBy = "gem",cascade = CascadeType.ALL)
    private List<GemPriceList> gemPriceLists;
}
