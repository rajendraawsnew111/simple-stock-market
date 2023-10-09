package com.jpmc.stockmarket.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jpmc.stockmarket.exception.ResourceNotFoundException;
import com.jpmc.stockmarket.service.TradingService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TradingControllerTest {

    public static final String CALCULATE_DIVIDEND_ENDPOINT = "/api/stock/calculateDividend/{stock}/{price}";
    public static final String CALCULATE_PE_RATIO_ENDPOINT = "/api/stock/calculatePeRatio/{stock}/{price}";

    public static final String CALCULATE_VOLUME_WEIGHTED_ENDPOINT = "/api/stock/volumeWeightedStockPrice/{timeInMinutes}";
    public static final String CALCULATE_GBCE_ENDPOINT = "/api/stock/calculateGBCE";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradingService tradingService;

    @Test
    public void calculateDividend() throws Exception {
        when(tradingService.calculateDividend("POP", new BigDecimal(6))).thenReturn(new BigDecimal(1.33));
        mockMvc.perform(get(CALCULATE_DIVIDEND_ENDPOINT, "POP", 6))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1.33")));
    }

    @Test
    public void calculateDividend_stockNotFound() throws Exception {
        when(tradingService.calculateDividend("POPA", new BigDecimal(6))).thenThrow(new ResourceNotFoundException("Stock item not found"));
        mockMvc.perform(get(CALCULATE_DIVIDEND_ENDPOINT, "POPA", 6))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Stock item not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void calculatePERatio() throws Exception {
        when(tradingService.calculatePERatio("POP", new BigDecimal(6))).thenReturn(new BigDecimal(0.75));
        mockMvc.perform(get(CALCULATE_PE_RATIO_ENDPOINT, "POP", 6))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0.75")));
    }

    @Test
    public void calculatePERatio_stockNotFound() throws Exception {
        when(tradingService.calculatePERatio("POPA", new BigDecimal(6))).thenThrow(new ResourceNotFoundException("Stock item not found"));
        mockMvc.perform(get(CALCULATE_PE_RATIO_ENDPOINT, "POPA", 6))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Stock item not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void calculateVolumeWeightedStockPrice() throws Exception {
        when(tradingService.calculateVolumeWeightedPrice(15)).thenReturn(new BigDecimal(12.5));
        mockMvc.perform(get(CALCULATE_VOLUME_WEIGHTED_ENDPOINT, 15))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("12.5")));
    }

    @Test
    public void calculateGBCE() throws Exception {
        when(tradingService.calculateGBCE()).thenReturn(new BigDecimal(10));
        mockMvc.perform(get(CALCULATE_GBCE_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("10")));
    }
}
