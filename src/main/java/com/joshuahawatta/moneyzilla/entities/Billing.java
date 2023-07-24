package com.joshuahawatta.moneyzilla.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return String.format(
                "%d - %s, %s, %f, %td/%tm/%tY, %s %s",
                id, name, description, price, boughtDate, boughtDate, boughtDate, type, description
        );
    }
}
