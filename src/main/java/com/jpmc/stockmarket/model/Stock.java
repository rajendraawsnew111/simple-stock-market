package com.jpmc.stockmarket.model;


import java.math.BigDecimal;

public class Stock {

    private String symbol;
    private StockType type;
    private BigDecimal lastDividend;
    private BigDecimal fixedDividend;
    private BigDecimal parValue;

    public Stock(String symbol, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
        this.symbol = symbol;
        this.type = type;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }


    public String getSymbol() {
        return symbol;
    }

    public StockType getType() {
        return type;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public void setFixedDividend(BigDecimal fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }
}
