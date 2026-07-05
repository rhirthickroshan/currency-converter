package com.currency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CurrencyRequest {

    @NotBlank
    private String fromCurrency;

    @NotBlank
    private Double amount;

    @NotBlank
    private String toCurrency;

}
