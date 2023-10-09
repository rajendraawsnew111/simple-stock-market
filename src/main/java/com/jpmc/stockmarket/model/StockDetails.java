package com.jpmc.stockmarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class StockDetails {
    private String stock;
    private BigDecimal price;
}
