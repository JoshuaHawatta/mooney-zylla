package com.joshuahawatta.moneyzilla.controllers;

import com.joshuahawatta.moneyzilla.dtos.user.CreateOrUpdateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.LoginDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.entities.User;
import com.joshuahawatta.moneyzilla.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody CreateOrUpdateAccountDto user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAccount(@AuthenticationPrincipal User loggedUser) {
        return new ResponseEntity<>(service.deleteAccount(loggedUser), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateAccount(
            @AuthenticationPrincipal User loggedUser,
            @RequestBody CreateOrUpdateAccountDto user
    ) {
        return new ResponseEntity<>(service.update(loggedUser, user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> findByEmail(@RequestBody LoginDto user) {
        return new ResponseEntity<>(service.login(user), HttpStatus.OK);
    }
}
