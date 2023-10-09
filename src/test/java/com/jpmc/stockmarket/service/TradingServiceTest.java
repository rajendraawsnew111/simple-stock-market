package com.jpmc.stockmarket.service;

import com.jpmc.stockmarket.exception.ResourceNotFoundException;
import com.jpmc.stockmarket.exception.StockException;
import com.jpmc.stockmarket.model.Stock;
import com.jpmc.stockmarket.model.StockType;
import com.jpmc.stockmarket.model.TradingDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TradingServiceTest {

    @Autowired
    TradingService tradingService;

    @MockBean
    StockItemService stockItemService;


    @Test
    public void calculatePERatio() throws StockException {
        when(stockItemService.getStockItem("POP")).thenReturn(new Stock("POP", StockType.COMMON, new BigDecimal(8),new BigDecimal(0),new BigDecimal(100)));
        Assertions.assertEquals(new BigDecimal(0.75), tradingService.calculatePERatio("POP", new BigDecimal(6)));
    }

    @Test
    public void calculatePERatio_validateStock() {
        StockException thrown = Assertions
            .assertThrows(StockException.class, () -> {
                tradingService.calculatePERatio("", new BigDecimal(6));
            }, "StockException was expected");

        Assertions.assertEquals("Stock Symbol is required", thrown.getMessage());
    }

    @Test
    public void calculatePERatio_validateStockPrice() {
        StockException thrown = Assertions
                .assertThrows(StockException.class, () -> {
                    tradingService.calculatePERatio("POP", new BigDecimal(0));
                }, "StockException was expected");

        Assertions.assertEquals("Price is required", thrown.getMessage());
    }

    @Test
    public void calculatePERatio_stockNotFound() {
        ResourceNotFoundException thrown = Assertions
                .assertThrows(ResourceNotFoundException.class, () -> {
                    tradingService.calculatePERatio("POPA", new BigDecimal(6));
                }, "StockException was expected");

        Assertions.assertEquals("Stock item not found", thrown.getMessage());
    }

    @Test
    public void calculateDividend() throws StockException {
        when(stockItemService.getStockItem("POP")).thenReturn(new Stock("POP", StockType.COMMON, new BigDecimal(8),new BigDecimal(0),new BigDecimal(100)));
        Assertions.assertEquals(1.33, tradingService.calculateDividend("POP", new BigDecimal(6)).doubleValue());
    }

    @Test
    public void calculateDividend_preferredType() throws StockException {
        when(stockItemService.getStockItem("GIN")).thenReturn(new Stock("GIN", StockType.PREFERRED, new BigDecimal(8),new BigDecimal(2),new BigDecimal(100)));
        Assertions.assertEquals(20, tradingService.calculateDividend("GIN", new BigDecimal(10)).doubleValue());
    }

    @Test
    public void calculateDividend_validateStockPrice() {
        StockException thrown = Assertions
                .assertThrows(StockException.class, () -> {
                    tradingService.calculateDividend("", new BigDecimal(6));
                }, "StockException was expected");

        Assertions.assertEquals("Stock Symbol is required", thrown.getMessage());
    }

    @Test
    public void calculateDividend_validateStock() {
        StockException thrown = Assertions
                .assertThrows(StockException.class, () -> {
                    tradingService.calculateDividend("POP", new BigDecimal(0));
                }, "StockException was expected");

        Assertions.assertEquals("Price is required", thrown.getMessage());
    }

    @Test
    public void calculateDividend_stockNotFound() {
        ResourceNotFoundException thrown = Assertions
                .assertThrows(ResourceNotFoundException.class, () -> {
                    tradingService.calculateDividend("POPA", new BigDecimal(6));
                }, "StockException was expected");

        Assertions.assertEquals("Stock item not found", thrown.getMessage());
    }

    @Test
    public void createTrade() {
        tradingService.clearAllTrades();
        createTradingRecordsForTesting();
        Assertions.assertEquals(4, tradingService.getAllTradeRecords().size());

    }

    @Test
    public void calculateVolumeWeightedPrice() {
        createTradingRecordsForTesting();
        Assertions.assertEquals(12.45, tradingService.calculateVolumeWeightedPrice(15).doubleValue());
    }

    @Test
    public void calculateGBCE() {
        createTradingRecordsForTesting();
        Assertions.assertEquals(11.89207115002721, tradingService.calculateGBCE().doubleValue());
    }

    private void createTradingRecordsForTesting() {
        TradingDetails tradingDetails1 = new TradingDetails("POP", new BigDecimal(10), 100, new BigDecimal(1000), "SELL", "trader1");
        TradingDetails tradingDetails2 = new TradingDetails("POP", new BigDecimal(20), 120, new BigDecimal(2400), "SELL", "trader1");
        TradingDetails tradingDetails3 = new TradingDetails("POP", new BigDecimal(10), 130, new BigDecimal(1300), "SELL", "trader1");
        TradingDetails tradingDetails4 = new TradingDetails("POP", new BigDecimal(10), 140, new BigDecimal(1400), "SELL", "trader1");
        tradingService.createTrade(tradingDetails1);
        tradingService.createTrade(tradingDetails2);
        tradingService.createTrade(tradingDetails3);
        tradingService.createTrade(tradingDetails4);
    }
}
