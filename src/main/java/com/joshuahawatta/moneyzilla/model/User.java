package com.joshuahawatta.moneyzilla.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "money", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @ColumnDefault("0.00")
    private BigDecimal money;

    @OneToMany(mappedBy = "user")
    private List<Billing> billings = new ArrayList<>();

    //CONSTRUCTORS_OVERLOADS
    public User() {}

    public User(Long id, String name, String email, String password, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.money = money != null ? new BigDecimal("" + money + "") : new BigDecimal(0.00);
    }

    @Override
    public String toString() {
        return String.format("%d - %s, %f, %s, %s", id, name, money, email, billings.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(money, user.money) && Objects.equals(billings, user.billings);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, email, password, money, billings); }

    //GETTERS_AND_SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public BigDecimal getMoney() { return money; }

    public void setMoney(BigDecimal money) { this.money = money; }

    public List<Billing> getBillings() { return billings; }

    public void setBillings(List<Billing> billings) { this.billings = billings; }
}
