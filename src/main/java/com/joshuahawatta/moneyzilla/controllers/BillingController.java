package com.joshuahawatta.moneyzilla.controllers;

import com.joshuahawatta.moneyzilla.dtos.billing.CreateOrUpdateBillingDto;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.services.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    BillingService service;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBilling(
            @RequestBody CreateOrUpdateBillingDto billing,
            @AuthenticationPrincipal User loggedUser
    ) {
        return new ResponseEntity<>(service.save(billing,loggedUser), HttpStatus.CREATED);
    }
}
