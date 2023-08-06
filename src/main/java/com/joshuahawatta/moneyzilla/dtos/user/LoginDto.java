package com.joshuahawatta.moneyzilla.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto (
        @NotNull(message = "Informe seu e-mail!") @NotBlank(message = "Informe seu e-mail!")
        String email,

        @NotNull(message = "Informe sua senha!") @NotBlank(message = "Informe sua senha!")
        String password
) {}