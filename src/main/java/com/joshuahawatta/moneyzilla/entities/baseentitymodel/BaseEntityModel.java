package com.joshuahawatta.moneyzilla.entities.baseentitymodel;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/** A basic Entity that won´t be created at database that will handle the createdAt and updatedAt fields for all database models. */
@MappedSuperclass
public abstract class BaseEntityModel {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** Just before an entity is registered on the database, the createdAt and updatedAt fields will be created. */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /** Just before an entity is updated on the database, the updatedAt field will be remade with a new LocalDateTime. */
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    /** Getters and setters. */
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
