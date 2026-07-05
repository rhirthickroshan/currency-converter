package com.currency.dto;

import com.currency.entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterRequest {

    private String name;

    @NotBlank
    @Email
    private String email;

    private String address;

    private Roles role = Roles.USER;

    private String password;

}
