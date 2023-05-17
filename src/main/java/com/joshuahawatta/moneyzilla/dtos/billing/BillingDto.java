package com.joshuahawatta.moneyzilla.dtos.billing;

import com.joshuahawatta.moneyzilla.models.Billing;
import com.joshuahawatta.moneyzilla.models.Users;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class BillingDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private LocalDateTime boughtDate;
    private Users users;

    public BillingDto(Billing billing) {
        id = billing.getId();
        name = billing.getName();
        type = billing.getType();
        description = billing.getDescription();
        price = billing.getPrice();
        boughtDate = billing.getBoughtDate();
        users = billing.getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillingDto that = (BillingDto) o;

        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(boughtDate, that.boughtDate) && Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, type, description, price, boughtDate, users); }

    //GETTERS_AND_SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getBoughtDate() { return boughtDate; }

    public void setBoughtDate(LocalDateTime boughtDate) { this.boughtDate = boughtDate; }

    public Users getUser() { return users; }

    public void setUser(Users users) { this.users = users; }
}
