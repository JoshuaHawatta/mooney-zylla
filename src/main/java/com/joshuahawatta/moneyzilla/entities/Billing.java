package com.joshuahawatta.moneyzilla.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "billings")
public class Billing extends BaseEntityModel implements Serializable {
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "bought_date", nullable = false)
    private LocalDateTime boughtDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User users;

    public Billing(String name, BigDecimal price, LocalDateTime boughtDate, String description, String type, User users) {
        this.name = name;
        this.price = new BigDecimal(price.toString());
        this.boughtDate = boughtDate;
        this.type = type;
        this.description = description;
        this.users = users;
    }
}
