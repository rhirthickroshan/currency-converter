package com.currency.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Data
public class CurrencyResponse {

    @NotBlank
    private String fromCurrency;

    @NotBlank
    private String toCurrency;

    private Double rate;

}
