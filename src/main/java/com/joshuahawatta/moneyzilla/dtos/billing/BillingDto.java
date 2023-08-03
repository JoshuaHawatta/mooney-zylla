package com.joshuahawatta.moneyzilla.dtos.billing;

import com.joshuahawatta.moneyzilla.entities.Billing;
import com.joshuahawatta.moneyzilla.entities.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class BillingDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private LocalDateTime boughtDate;
    private User users;

    public BillingDto(Billing billing) {
        id = billing.getId();
        name = billing.getName();
        type = billing.getType();
        description = billing.getDescription();
        price = billing.getPrice();
        boughtDate = billing.getBoughtDate();
        users = billing.getUsers();
    }
}
