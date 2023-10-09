package com.jpmc.stockmarket.service;

import com.jpmc.stockmarket.model.Stock;
import com.jpmc.stockmarket.model.StockType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
public class StockItemService {

    Map<String, Stock> stockItems;
    public void initializeSock() {
        Stock stockDetail1 = new Stock("TEA", StockType.COMMON, new BigDecimal(0),new BigDecimal(0),new BigDecimal(100));
        Stock stockDetail2 = new Stock("POP", StockType.COMMON, new BigDecimal(8),new BigDecimal(0),new BigDecimal(100));
        Stock stockDetail3 = new Stock("ALE", StockType.COMMON, new BigDecimal(23),new BigDecimal(0),new BigDecimal(60));
        Stock stockDetail4 = new Stock("GIN", StockType.PREFERRED, new BigDecimal(8),new BigDecimal(2),new BigDecimal(100));
        Stock stockDetail5 = new Stock("JOE", StockType.COMMON, new BigDecimal(13),new BigDecimal(0),new BigDecimal(250));

       stockItems = Map.of("TEA",stockDetail1, "POP", stockDetail2, "ALE", stockDetail3, "GIN", stockDetail4, "JOE", stockDetail5);
    }

    public Stock getStockItem(String symbol) {
        return stockItems.get(symbol);
    }

    public List<Stock> getAllStockItems() {
        return stockItems.values().stream().toList();
    }
}
