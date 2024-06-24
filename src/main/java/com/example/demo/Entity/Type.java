package com.example.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeID;

    @NotNull
    private String typeName;

    private int warrantyTime;

    @OneToMany(mappedBy = "type")
    private List<Product> products;
}
