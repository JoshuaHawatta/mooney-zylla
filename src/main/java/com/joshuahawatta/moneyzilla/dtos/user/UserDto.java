package com.joshuahawatta.moneyzilla.dtos.user;

import com.joshuahawatta.moneyzilla.entities.Billing;
import com.joshuahawatta.moneyzilla.entities.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<Billing> billings;

    public UserDto(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.money = user.getMoney();
        this.billings = user.getBillings();
    }
}
