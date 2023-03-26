package com.joshuahawatta.moneyzilla.repositories;

import com.joshuahawatta.moneyzilla.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
