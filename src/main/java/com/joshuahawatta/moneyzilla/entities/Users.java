package com.joshuahawatta.moneyzilla.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
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

    @Column(name = "email", nullable = false)
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

    public Users(String name, String email, String password, BigDecimal money) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.money = money != null ? new BigDecimal(money.toString()) : BigDecimal.valueOf(0.00);
    }

    @Override
    public String toString() {
        return String.format("- %d = [ %s, %f, %s, %s]", id, name, money, email, billings);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(new SimpleGrantedAuthority("USER_ROLE")); }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
