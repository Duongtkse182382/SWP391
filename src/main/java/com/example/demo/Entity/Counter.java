package com.example.demo.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer counterID;
    
    @Column(name="counterName")
     String counterName;
    
    @Column(name="active")
     boolean active;
    
    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     List<Product> product;

}
