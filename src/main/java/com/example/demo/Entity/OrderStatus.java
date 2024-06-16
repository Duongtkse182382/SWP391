package com.example.demo.Entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderstatusID;

    @NotNull(message="Vui lòng không để trống trường này")
    private String name;
    
    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}

