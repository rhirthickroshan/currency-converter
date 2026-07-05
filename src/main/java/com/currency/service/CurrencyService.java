package com.currency.service;

import com.currency.entity.Currency;
import com.currency.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(String id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
    }

    public Currency updateCurrency(String id, Currency currency) {
        Currency existingCurrency = getCurrencyById(id);

        existingCurrency.setFromCurrency(currency.getFromCurrency());
        existingCurrency.setToCurrency(currency.getToCurrency());
        existingCurrency.setRate(currency.getRate());

        return currencyRepository.save(existingCurrency);
    }

    public void deleteCurrency(String id) {
        Currency existingCurrency = getCurrencyById(id);
        currencyRepository.delete(existingCurrency);
    }
}