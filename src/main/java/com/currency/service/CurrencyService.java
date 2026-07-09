package com.currency.service;

import com.currency.entity.Currency;
import com.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private static final Logger logger =
            LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository currencyRepository;

    public Currency createCurrency(Currency currency) {

        logger.info("Create Currency request received.");

        logger.debug("Saving Currency: {}", currency);

        Currency savedCurrency = currencyRepository.save(currency);

        logger.info("Currency created successfully with id: {}",
                savedCurrency.getFromCurrency());

        return savedCurrency;
    }

    public List<Currency> getAllCurrencies() {

        logger.info("Fetching all currencies.");

        List<Currency> currencies = currencyRepository.findAll();

        logger.debug("Total currencies fetched: {}", currencies.size());

        return currencies;
    }

    public Currency getCurrencyById(String id) {

        logger.info("Fetching currency with id: {}", id);

        return currencyRepository.findById(id)
                .map(currency -> {
                    logger.debug("Currency found: {}", currency);
                    return currency;
                })
                .orElseThrow(() -> {
                    logger.warn("Currency not found with id: {}", id);
                    return new RuntimeException("Currency not found with id: " + id);
                });
    }

    public Currency updateCurrency(String id, Currency currency) {

        logger.info("Updating currency with id: {}", id);

        Currency existingCurrency = getCurrencyById(id);

        logger.debug("Existing Currency: {}", existingCurrency);

        existingCurrency.setFromCurrency(currency.getFromCurrency());
        existingCurrency.setToCurrency(currency.getToCurrency());
        existingCurrency.setRate(currency.getRate());

        Currency updatedCurrency = currencyRepository.save(existingCurrency);

        logger.info("Currency updated successfully with id: {}", id);

        return updatedCurrency;
    }

    public void deleteCurrency(String id) {

        logger.info("Delete request received for currency id: {}", id);

        Currency existingCurrency = getCurrencyById(id);

        logger.debug("Deleting Currency: {}", existingCurrency);

        currencyRepository.delete(existingCurrency);

        logger.info("Currency deleted successfully with id: {}", id);
    }
}