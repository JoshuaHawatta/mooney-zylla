package com.joshuahawatta.moneyzilla.repositories;

import com.joshuahawatta.moneyzilla.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
