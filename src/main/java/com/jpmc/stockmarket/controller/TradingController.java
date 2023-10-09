package com.jpmc.stockmarket.controller;

import com.jpmc.stockmarket.exception.StockException;
import com.jpmc.stockmarket.model.StockDetails;
import com.jpmc.stockmarket.model.TradingDetails;
import com.jpmc.stockmarket.service.TradingService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/stock")
public class TradingController {

    @Autowired
    public TradingService tradingService;

    @GetMapping("/calculateDividend/{stock}/{price}")
    public BigDecimal calculateDividend(@PathVariable String stock, @PathVariable BigDecimal price) throws StockException {
        return tradingService.calculateDividend(stock, price);
    }

    @GetMapping("/calculatePeRatio/{stock}/{price}")
    public BigDecimal calculatePERatio(@PathVariable String stock, @PathVariable BigDecimal price) throws StockException {
        return tradingService.calculatePERatio(stock, price);
    }

    @PostMapping("/trade")
    public void createTrade(@RequestBody TradingDetails tradingDetails) {
        tradingService.createTrade(tradingDetails);
    }

    @GetMapping("/volumeWeightedStockPrice/{timeInMinutes}")
    public BigDecimal calculateVolumeWeightedStockPrice(@PathVariable Integer timeInMinutes) {
        return tradingService.calculateVolumeWeightedPrice(timeInMinutes);
    }

    @GetMapping("/calculateGBCE")
    public BigDecimal calculateGBCE() {
        return tradingService.calculateGBCE();
    }
}
