package com.jpmc.stockmarket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TradingDetails {
    private String stock;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
    private String tradeType;
    private String trader;
}
