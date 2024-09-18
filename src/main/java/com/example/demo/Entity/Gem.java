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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)    
public class Gem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int gemID;
    String gemCode;
    String gemName;
    String origin;
    float carat;
    String color;             
    String clarity;
    String cut;

    @OneToMany(mappedBy = "gem",cascade = CascadeType.ALL)
    List<GemPriceList> gemPriceLists;
}
