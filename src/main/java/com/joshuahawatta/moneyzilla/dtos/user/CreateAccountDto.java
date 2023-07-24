package com.joshuahawatta.moneyzilla.dtos.user;

import java.math.BigDecimal;

public class CreateAccountDto {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private BigDecimal money;

    public CreateAccountDto(String name, String email, String password, String confirmPassword, BigDecimal money) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.money = money;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public BigDecimal getMoney() { return money; }

    public void setMoney(BigDecimal money) { this.money = money; }
}