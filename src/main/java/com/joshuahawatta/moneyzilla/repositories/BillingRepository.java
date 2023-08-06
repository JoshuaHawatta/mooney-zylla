package com.joshuahawatta.moneyzilla.repositories;

import com.joshuahawatta.moneyzilla.entities.Billing;
import com.joshuahawatta.moneyzilla.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    @Query("SELECT b FROM Billing b WHERE b.users = ?1")
    List<Billing> findAllByUserId(User loggedUser);
}
