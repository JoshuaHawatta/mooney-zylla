package com.joshuahawatta.moneyzilla.models;

import com.joshuahawatta.moneyzilla.entities.baseentitymodel.BaseEntityModel;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Roles extends BaseEntityModel implements Serializable, GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @Override
    public String getAuthority() { return role; }

    //GETTERS_AND_SETTERS
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
