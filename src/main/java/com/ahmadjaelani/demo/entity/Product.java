package com.ahmadjaelani.demo.entity;

import com.ahmadjaelani.demo.model.ProductTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 200)
    private ProductTypeEnum type;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
