package com.jpmc.stockmarket.service;

import com.jpmc.stockmarket.dao.TradingDao;
import com.jpmc.stockmarket.exception.ResourceNotFoundException;
import com.jpmc.stockmarket.exception.StockException;
import com.jpmc.stockmarket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradingService {

    @Autowired
    StockItemService stockItemService;

    @Autowired
    TradingDao tradingDao;

    public BigDecimal calculateDividend(String stockSymbol, BigDecimal price) throws StockException {
        validateStockDetails(stockSymbol, price);
        Stock stock = stockItemService.getStockItem(stockSymbol);
        if ( stock!= null) {
            if (stock.getLastDividend() == BigDecimal.ZERO) return BigDecimal.ZERO;

            if (StockType.COMMON.equals(stock.getType())) {
                return stock.getLastDividend().divide(price, 2, RoundingMode.HALF_UP);
            } else {
                return stock.getFixedDividend().multiply(stock.getParValue())
                        .divide(price, 2, RoundingMode.HALF_UP);
            }
        } else {
            throw new ResourceNotFoundException("Stock item not found");
        }
    }

    public BigDecimal calculatePERatio(String stockSymbol, BigDecimal price) throws StockException {
        validateStockDetails(stockSymbol, price);
        Stock stock = stockItemService.getStockItem(stockSymbol);
        if ( stock!= null) {
            if (stock.getLastDividend() == BigDecimal.ZERO) return BigDecimal.ZERO;
            return price.divide(stock.getLastDividend());
        } else {
            throw new ResourceNotFoundException("Stock item not found");
        }
    }

    public void createTrade(TradingDetails tradingDetails) {
        tradingDao.save(createTradingRecord(tradingDetails));
    }

    public BigDecimal calculateVolumeWeightedPrice(Integer minutes) {
        List<TradingRecord> eligibleTrades = tradingDao.
                getAll()
                .stream()
                .filter(t -> LocalDateTime.now().minusMinutes(minutes).isBefore(t.getDateTime()))
                .collect(Collectors.toList());
        if(!eligibleTrades.isEmpty()) {
            return eligibleTrades.stream().map(t -> t.getAmount()).reduce(BigDecimal::add).get()
                    .divide(new BigDecimal(eligibleTrades.stream()
                            .map(t -> t.getQuantity())
                            .reduce(Integer::sum).get()), 2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal calculateGBCE() {
        List<TradingRecord> allTradingRecords = tradingDao.getAll();
        if (!allTradingRecords.isEmpty()) {
            return new BigDecimal(Math.pow(
                    allTradingRecords
                            .stream()
                            .map(t->t.getPrice())
                            .reduce(BigDecimal::multiply).get().doubleValue(),
                    (double)1/allTradingRecords.size()));
        } else {
            return BigDecimal.ZERO;
        }
    }

    private TradingRecord createTradingRecord(TradingDetails tradingDetails) {
        return new TradingRecord(
                LocalDateTime.now(),
                tradingDetails.getTrader(),
                tradingDetails.getPrice(),
                tradingDetails.getQuantity(),
                tradingDetails.getAmount(),
                TradingType.valueOf(tradingDetails.getTradeType())
        );
    }

    private void validateStockDetails(String stock, BigDecimal price) throws StockException {
        if (stock.isEmpty()) throw new StockException("Stock Symbol is required");
        if (price.equals(BigDecimal.ZERO)) throw new StockException("Price is required");
    }

    public List<TradingRecord> getAllTradeRecords() {
        return tradingDao.getAll();
    }

    public void clearAllTrades() {
        tradingDao.clearAllTrades();
    }
}
