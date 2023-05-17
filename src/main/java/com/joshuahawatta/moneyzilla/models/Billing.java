package com.joshuahawatta.moneyzilla.models;

import com.joshuahawatta.moneyzilla.entities.baseentitymodel.BaseEntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "billings")
public class Billing extends BaseEntityModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @NotBlank(message = "Informe o nome da compra!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Informe o valor da compra!")
    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @NotBlank(message = "Informe o tipo da compra!")
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @NotBlank(message = "Informe a data da compra!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "bought_date", nullable = false)
    private LocalDateTime boughtDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    //CONSTRUCTORS_OVERLOADS
    public Billing() {}

    public Billing(
        Long id,
        String name,
        BigDecimal price,
        String type,
        String description,
        LocalDateTime boughtDate,
        Users users
    ) {
        this.id = id;
        this.name = name;
        this.price = new BigDecimal("" + price + "");
        this.type = type;
        this.description = description;
        this.boughtDate = boughtDate;
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Billing billing = (Billing) o;

        return Objects.equals(id, billing.id) && Objects.equals(name, billing.name) && Objects.equals(price, billing.price) && Objects.equals(type, billing.type) && Objects.equals(description, billing.description) && Objects.equals(boughtDate, billing.boughtDate) && Objects.equals(users, billing.users);
    }

    @Override
    public String toString() {
        return String.format(
                "%d - %s, %s, %f, %td/%tm/%tY, %s %s",
                id, name, description, price, boughtDate, boughtDate, boughtDate, type, description
        );
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, price, type, description, boughtDate, users); }

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

    public Users getUser() { return users; }

    public void setUser(Users users) { this.users = users; }
}
