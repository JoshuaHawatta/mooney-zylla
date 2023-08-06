package com.joshuahawatta.moneyzilla.services;

import com.joshuahawatta.moneyzilla.configurations.validations.Validations;
import com.joshuahawatta.moneyzilla.dtos.billing.BillingDto;
import com.joshuahawatta.moneyzilla.dtos.billing.CreateOrUpdateBillingDto;
import com.joshuahawatta.moneyzilla.entities.Billing;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.repositories.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BillingService {
    @Autowired
    BillingRepository repository;

    @Autowired
    Validations validations;

    public Map<String, Object> save (CreateOrUpdateBillingDto billing, User loggedUser) {
        validations.validate(billing);

        if (loggedUser == null) throw new NullPointerException("Você não está autenticado, faça seu login!");

        var newBilling = new Billing(
                billing.name(),
                billing.price(),
                billing.boughtDate(),
                billing.description(),
                billing.type(),
                loggedUser
        );

        repository.save(newBilling);

        var results = new HashMap<String, Object>();

        results.put("message", "Despesa criada com sucesso!");
        results.put("billing", new BillingDto(newBilling));

        return results;
    }
}
