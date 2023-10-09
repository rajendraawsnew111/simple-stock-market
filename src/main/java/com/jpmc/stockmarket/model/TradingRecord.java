package com.jpmc.stockmarket.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TradingRecord {
    private Integer id;
    private LocalDateTime dateTime;
    private String trader;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
    private TradingType tradingType;

    public TradingRecord(LocalDateTime dateTime, String trader, BigDecimal price, Integer quantity, BigDecimal amount, TradingType tradingType) {
        this.dateTime = dateTime;
        this.trader = trader;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.tradingType = tradingType;
    }
}