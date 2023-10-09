package com.jpmc.stockmarket.config;


import com.jpmc.stockmarket.service.StockItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialStockConfig {
    @Bean
    public StockItemService stockItemService() {
       StockItemService stockService = new StockItemService();
       stockService.initializeSock();
       return stockService;
    }

}
