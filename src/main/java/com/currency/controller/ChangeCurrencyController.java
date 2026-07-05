package com.currency.controller;


import com.currency.dto.CurrencyRequest;
import com.currency.service.ChangeCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeCurrencyController {

    private final ChangeCurrencyService changeCurrencyService;


    public ResponseEntity<?> changeCurrency(CurrencyRequest currencyRequest){
        return changeCurrencyService.changeCurrency(currencyRequest);
    }
}
