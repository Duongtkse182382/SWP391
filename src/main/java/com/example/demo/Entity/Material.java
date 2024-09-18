package com.example.demo.Entity;

import java.util.List;

import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialID;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "material")
    private List<MaterialPriceList> materialPriceLists;
}
