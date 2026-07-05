package com.currency.controller;

import com.currency.entity.Currency;
import com.currency.service.CurrencyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public Currency createCurrency(@Valid @RequestBody Currency currency) {
        return currencyService.createCurrency(currency);
    }

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable String id) {
        return currencyService.getCurrencyById(id);
    }

    @PutMapping("/{id}")
    public Currency updateCurrency(@PathVariable String id, @Valid @RequestBody Currency currency) {
        return currencyService.updateCurrency(id, currency);
    }

    @DeleteMapping("/{id}")
    public String deleteCurrency(@PathVariable String id) {
        currencyService.deleteCurrency(id);
        return "Currency deleted successfully";
    }
}