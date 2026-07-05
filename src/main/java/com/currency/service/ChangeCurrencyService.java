package com.currency.service;


import com.currency.dto.CurrencyRequest;
import com.currency.dto.CurrencyResponse;
import com.currency.entity.ChangeCurrency;
import com.currency.exception.custom.CurrencyException;
import com.currency.repository.ChangeCurrencyRepository;
import com.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeCurrencyService {


    private final ChangeCurrencyRepository changeCurrencyRepository;

    public ResponseEntity<?> changeCurrency(CurrencyRequest currencyRequest) {
        double result = 0.0;
        if(currencyRequest == null){
            throw new CurrencyException("The currency Request cannot be null");
        }

        String fromCurrency = currencyRequest.getFromCurrency();
        String toCurrency = currencyRequest.getToCurrency();

        if(fromCurrency != null && toCurrency!=null){
            String key = fromCurrency+toCurrency;
            Optional<ChangeCurrency> changeCurrency = changeCurrencyRepository.findById(key);
            if(changeCurrency.isEmpty()){
                throw new CurrencyException("the specific from and to currency is not available");
            }
            ChangeCurrency currency = changeCurrency.get();
            String check = currency.getChoose();
            Double changeAmount = currencyRequest.getAmount();
            if(Objects.equals(check, "Y")){
                result = changeAmount * currency.getAmount();
            }
            else{
                result = currency.getAmount() / changeAmount ;
            }

            CurrencyResponse currencyResponse = new CurrencyResponse();
            currencyResponse.setFromCurrency(currencyRequest.getFromCurrency());
            currencyResponse.setToCurrency(currencyRequest.getToCurrency());
            currencyResponse.setRate(result);
            return new ResponseEntity<>(currencyResponse, HttpStatus.OK);

        }
        else {
            throw new CurrencyException("From and To Currency is not available at the bank");
        }
    }
}
