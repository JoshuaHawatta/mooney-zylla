package com.joshuahawatta.moneyzilla.dtos.billing;

import com.joshuahawatta.moneyzilla.entities.User;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrUpdateBillingDto (
        @NotNull(message = "Informe o nome da compra!") @NotBlank(message = "Informe o nome da compra!")
        String name,

        @NotNull(message = "Informe o tipo da compra!") @NotBlank(message = "Informe o tipo da compra!")
        String type,

        String description,

        @Negative(message = "Valor inválido")
        @NotNull(message = "Informe o valor da compra!")
        BigDecimal price,

        @NotNull(message = "Informe a data da compra!")
        LocalDateTime boughtDate,

        User user
){}
