package com.example.demo.Entity;

import javax.persistence.*;
import java.util.Date;
import lombok.*;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promotionID;

    @NotNull(message="Vui lòng không để trống trường này")
    private String promotionCode;
    private String promotionName;
    private String promotionPercent;
    private boolean Status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String applyFor;
}
