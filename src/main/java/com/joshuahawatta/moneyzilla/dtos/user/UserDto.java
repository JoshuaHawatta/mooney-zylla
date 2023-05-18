package com.joshuahawatta.moneyzilla.dtos.user;

import com.joshuahawatta.moneyzilla.models.Billing;
import com.joshuahawatta.moneyzilla.models.Roles;
import com.joshuahawatta.moneyzilla.models.Users;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<Billing> billings;
    private List<Roles> roles;

    public UserDto(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.money = users.getMoney();
        this.billings = users.getBillings();
        this.roles = users.getRoles();
    }

    /**
     * Class main methods
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(money, userDto.money) && Objects.equals(billings, userDto.billings);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, email, money, billings); }

    /**
     * Getters and setters
     */
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

    public List<Roles> getRoles() { return roles; }

    public void setRoles(List<Roles> roles) { this.roles = roles; }
}
