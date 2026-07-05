package com.currency.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("change_currency")
public class ChangeCurrency {

    @Id
    private String key;

    private String choose;

    private Double amount;

}
