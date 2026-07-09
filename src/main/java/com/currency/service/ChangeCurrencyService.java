package com.currency.service;

import com.currency.dto.CurrencyRequest;
import com.currency.dto.CurrencyResponse;
import com.currency.entity.ChangeCurrency;
import com.currency.exception.custom.CurrencyException;
import com.currency.repository.ChangeCurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeCurrencyService {

    private static final Logger logger =
            LoggerFactory.getLogger(ChangeCurrencyService.class);

    private final ChangeCurrencyRepository changeCurrencyRepository;

    public ResponseEntity<?> changeCurrency(CurrencyRequest currencyRequest) {

        logger.info("Currency conversion request received.");

        double result = 0.0;

        if (currencyRequest == null) {
            logger.warn("Currency request is null.");
            throw new CurrencyException("The currency Request cannot be null");
        }

        String fromCurrency = currencyRequest.getFromCurrency();
        String toCurrency = currencyRequest.getToCurrency();

        logger.debug("From Currency: {}, To Currency: {}, Amount: {}",
                fromCurrency,
                toCurrency,
                currencyRequest.getAmount());

        if (fromCurrency != null && toCurrency != null) {

            String key = fromCurrency + toCurrency;

            logger.debug("Looking up exchange rate for key: {}", key);

            Optional<ChangeCurrency> changeCurrency =
                    changeCurrencyRepository.findById(key);

            if (changeCurrency.isEmpty()) {

                logger.warn("Exchange rate not found for currency pair: {}", key);

                throw new CurrencyException(
                        "The specific from and to currency is not available");
            }

            ChangeCurrency currency = changeCurrency.get();

            logger.debug("Exchange rate found successfully.");

            String check = currency.getChoose();
            Double changeAmount = currencyRequest.getAmount();

            if (Objects.equals(check, "Y")) {

                logger.debug("Using multiplication formula.");

                result = changeAmount * currency.getAmount();

            } else {

                logger.debug("Using division formula.");

                result = currency.getAmount() / changeAmount;
            }

            logger.info(
                    "Currency converted successfully from {} to {}. Converted Amount: {}",
                    fromCurrency,
                    toCurrency,
                    result);

            CurrencyResponse currencyResponse = new CurrencyResponse();
            currencyResponse.setFromCurrency(fromCurrency);
            currencyResponse.setToCurrency(toCurrency);
            currencyResponse.setRate(result);

            return new ResponseEntity<>(currencyResponse, HttpStatus.OK);

        } else {

            logger.warn("From Currency or To Currency is missing.");

            throw new CurrencyException(
                    "From and To Currency is not available at the bank");
        }
    }
}