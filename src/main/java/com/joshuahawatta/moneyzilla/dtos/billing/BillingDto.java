package com.joshuahawatta.moneyzilla.dtos.billing;

import com.joshuahawatta.moneyzilla.models.Billing;
import com.joshuahawatta.moneyzilla.models.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillingDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private LocalDateTime boughtDate;
    private User user;

    public BillingDto(Billing billing) {
        id = billing.getId();
        name = billing.getName();
        type = billing.getType();
        description = billing.getDescription();
        price = billing.getPrice();
        boughtDate = billing.getBoughtDate();
        user = billing.getUser();
    }
}
