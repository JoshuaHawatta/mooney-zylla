package com.joshuahawatta.moneyzilla.repositories;

import com.joshuahawatta.moneyzilla.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
