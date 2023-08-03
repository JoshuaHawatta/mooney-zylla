package com.joshuahawatta.moneyzilla.dtos.user;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateAccountDto {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private BigDecimal money;
}