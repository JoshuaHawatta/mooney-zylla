package com.joshuahawatta.moneyzilla.dtos.user;

import java.math.BigDecimal;
public record CreateAccountDto (
     String name,
     String email,
     String password,
     String confirmPassword,
     BigDecimal money
) {}