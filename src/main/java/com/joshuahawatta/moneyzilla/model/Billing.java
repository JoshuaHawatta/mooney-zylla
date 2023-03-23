package com.joshuahawatta.moneyzilla.model;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "billings")
public class Billing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "bought_date", nullable = false)
    private LocalDateTime boughtDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //CONSTRUCTORS_OVERLOADS
    public Billing() {}

    public Billing(
            Long id,
        String name,
        BigDecimal price,
        String type,
        String description,
        LocalDateTime boughtDate,
        User user)
    {
        this.id = id;
        this.name = name;
        this.price = new BigDecimal("" + price + "");
        this.type = type;
        this.description = description;
        this.boughtDate = boughtDate;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Billing billing = (Billing) o;

        return Objects.equals(id, billing.id) && Objects.equals(name, billing.name) && Objects.equals(price, billing.price) && Objects.equals(type, billing.type) && Objects.equals(description, billing.description) && Objects.equals(boughtDate, billing.boughtDate) && Objects.equals(user, billing.user);
    }

    @Override
    public String toString() {
        return String.format(
                "%d - %s, %s, %f, %td/%tm/%tY, %s %s",
                id, name, description, price, boughtDate, boughtDate, boughtDate, type, description
        );
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, price, type, description, boughtDate, user); }

    //GETTERS_AND_SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getBoughtDate() { return boughtDate; }

    public void setBoughtDate(LocalDateTime boughtDate) { this.boughtDate = boughtDate; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
