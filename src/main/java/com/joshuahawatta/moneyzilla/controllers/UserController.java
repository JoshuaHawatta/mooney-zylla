package com.joshuahawatta.moneyzilla.controllers;

import com.joshuahawatta.moneyzilla.dtos.user.CreateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody CreateAccountDto user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateAccount(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<>(service.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        service.deleteById(id);

        Map<String, String> resultMessage = new HashMap<>();
        resultMessage.put("message", "Até mais, obrigado pelos peixes!");

        return new ResponseEntity<>(resultMessage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> findByEmail(@RequestBody User user) {
        return new ResponseEntity<>(service.login(user), HttpStatus.OK);
    }
}
