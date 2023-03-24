package com.joshuahawatta.moneyzilla.dto.billing;

import com.joshuahawatta.moneyzilla.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillingDto(Long id, String name, BigDecimal price, String type, String description, LocalDateTime boughtDate, User user) {}
