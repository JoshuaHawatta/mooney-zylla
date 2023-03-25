package com.joshuahawatta.moneyzilla.dtos.user;

import com.joshuahawatta.moneyzilla.models.Billing;
import com.joshuahawatta.moneyzilla.models.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<Billing> billings;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.money = user.getMoney();
        this.billings = user.getBillings();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(money, userDto.money) && Objects.equals(billings, userDto.billings);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, email, money, billings); }

    //GETTERS_AND_SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public BigDecimal getMoney() { return money; }

    public void setMoney(BigDecimal money) { this.money = money; }

    public List<Billing> getBillings() { return billings; }

    public void setBillings(List<Billing> billings) { this.billings = billings; }
}
