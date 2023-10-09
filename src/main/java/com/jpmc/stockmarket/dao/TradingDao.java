package com.jpmc.stockmarket.dao;

import com.jpmc.stockmarket.model.TradingRecord;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TradingDao {
    private Map<Integer, TradingRecord> persistedTradingRecords;
    private Integer idIndex;

    public TradingDao() {
        this.persistedTradingRecords = new HashMap<>();
        idIndex = 1;
    }

    public void save(TradingRecord tradingRecord) {
        tradingRecord.setId(idIndex++);
        persistedTradingRecords.put(tradingRecord.getId(), tradingRecord);
    }
    public List<TradingRecord> getAll() {
        return persistedTradingRecords.values().stream().toList();
    }

    public void clearAllTrades() {
        persistedTradingRecords = new HashMap<>();
        idIndex = 1;
    }
}
