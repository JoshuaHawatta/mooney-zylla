package com.joshuahawatta.moneyzilla.dtos.user;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public record CreateOrUpdateAccountDto(
        @NotNull(message = "Informe seu nome!") @NotBlank(message = "Informe seu nome!")
        String name,

        @NotNull(message = "Informe seu e-mail!") @NotBlank(message = "Informe seu e-mail!")
        String email,

        @Size(min = 8, message = "A senha deve ter no mínimo 8 caractéres!")
        @NotNull(message = "Informe sua senha!") @NotBlank(message = "Informe sua senha!")
        String password,

        @NotNull(message = "Confirme sua senha!") @NotBlank(message = "Confirme sua senha!")
        String confirmPassword,

        @Min(value = 0, message = "Valor inválido")
        @NotNull(message = "Informe o quanto você deseja vigiar!")
        BigDecimal money
) {}