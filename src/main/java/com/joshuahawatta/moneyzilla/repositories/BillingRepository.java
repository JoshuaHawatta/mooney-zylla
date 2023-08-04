package com.joshuahawatta.moneyzilla.repositories;

import com.joshuahawatta.moneyzilla.entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {}
