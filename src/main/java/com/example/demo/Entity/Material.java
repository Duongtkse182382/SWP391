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
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialID;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "material")
    private List<MaterialPriceList> materialPriceLists;
}
