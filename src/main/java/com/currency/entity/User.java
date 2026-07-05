package com.currency.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("user")
public class User {

    @Id
    private String id;

    private String name;

    private String address;

    @NotBlank
    @Email
    private String email;

    private Roles role = Roles.USER;

    private String password;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public void beforeInsert(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();

        if(role == null){
            this.role = Roles.USER;
        }
    }

    public void beforeUpdate(){
        this.updatedAt = LocalDate.now();
    }



}
