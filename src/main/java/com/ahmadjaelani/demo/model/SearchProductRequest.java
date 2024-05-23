package com.ahmadjaelani.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
    private String name;

    private String type;

    private BigDecimal lowPrice;

    private BigDecimal highPrice;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
