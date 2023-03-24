package com.joshuahawatta.moneyzilla.dto.user;

import com.joshuahawatta.moneyzilla.model.Billing;
import java.math.BigDecimal;
import java.util.List;

public record UserDto(Long id, String name, String email, BigDecimal money, List<Billing> billings) {}
