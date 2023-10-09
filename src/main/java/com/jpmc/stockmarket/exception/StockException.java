package com.jpmc.stockmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockException extends Exception{
    public StockException(String errorMessage) {
        super(errorMessage);
    }
}
