package com.joshuahawatta.moneyzilla.models;

import com.joshuahawatta.moneyzilla.entities.baseentitymodel.BaseEntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users extends BaseEntityModel implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @NotBlank(message = "Nome obrigatório!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "E-mail obrigatório!")
    @Email(message = "E-mail inválido!")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha obrigatória!")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caractéres!")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "money", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @ColumnDefault("0.00")
    private BigDecimal money;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Billing> billings = new ArrayList<>();

    /**
     * Creating join table rules that are made of the users ids and roles ids
     */
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
        name = "user_roles",
        uniqueConstraints =  @UniqueConstraint(columnNames = { "users_id", "roles_id" }, name = "unique_user_role"),

        joinColumns = @JoinColumn(
            name                 =  "users_id",
            referencedColumnName = "id",
            table                = "users",
            foreignKey           = @ForeignKey(name = "users_fk", value = ConstraintMode.CONSTRAINT)
        ),

        inverseJoinColumns = @JoinColumn(
            name                 = "roles_id",
            referencedColumnName = "id",
            table                = "roles",
            foreignKey           = @ForeignKey(name = "roles_fk", value = ConstraintMode.CONSTRAINT),
            updatable            = false
        )
    )
    private List<Roles> roles;

    /**
     * Constructor overloads
     */
    public Users() {}

    public Users(String name, String email, String password, BigDecimal money) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.money = money != null ? new BigDecimal("" + money + "") : BigDecimal.valueOf(0.00);
    }

    /**
     * Basic class methods
     */
    @Override
    public String toString() {
        return String.format("- %d = [ %s, %f, %s, %s]", id, name, money, email, billings.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        return Objects.equals(id, users.id) && Objects.equals(name, users.name) && Objects.equals(email, users.email) && Objects.equals(password, users.password) && Objects.equals(money, users.money) && Objects.equals(billings, users.billings);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, email, password, money, billings); }

    /**
     * Getters and setters
     */
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public BigDecimal getMoney() { return money; }

    public void setMoney(BigDecimal money) { this.money = money; }

    public List<Billing> getBillings() { return billings; }

    public void setBillings(List<Billing> billings) { this.billings = billings; }

    /**
     * Users authorizations list. Example: ROLE_ADMIN or ROLE_USER
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    /**
     * UserDetails interface methods (DO NOT remove the others email and password getters e setters).
     */
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.email; }

    /**
     * Authentication methods (also from UserDetails interface).
     */
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
