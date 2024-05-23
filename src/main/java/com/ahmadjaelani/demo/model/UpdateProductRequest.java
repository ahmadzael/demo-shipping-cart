package com.ahmadjaelani.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

    @NotBlank
    private String id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String type;

    @Size(max = 100)
    private BigDecimal price;

}
