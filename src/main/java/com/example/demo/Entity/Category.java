package com.example.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int categoryID;

    @NotNull(message = "Vui lòng không để trống trường này")
    String catName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products;
    
}
