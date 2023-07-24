package com.joshuahawatta.moneyzilla.controllers;

import com.joshuahawatta.moneyzilla.dtos.user.CreateAccountDto;
import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.helpers.responses.Response;
import com.joshuahawatta.moneyzilla.helpers.responses.ResponseResult;
import com.joshuahawatta.moneyzilla.helpers.responses.ResponseResultWIthMessage;
import com.joshuahawatta.moneyzilla.entities.Users;
import com.joshuahawatta.moneyzilla.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> findAll() {
        return Response.sendResponse(new ResponseResult<>(200, service.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<Map<String, Object>>> save(@RequestBody CreateAccountDto users) {
        Map<String, Object> results = service.save(users);

        return Response.sendResponse(
                new ResponseResultWIthMessage<>(201, results, "Olá! È muito bom te ver aqui!")
        );
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<Response<UserDto>> updateAccount(@PathVariable Long id, @RequestBody Users users) {
        UserDto newUser = service.update(id, users);

        return Response.sendResponse(new ResponseResult<>(201, newUser));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Response<String>> deleteAccount(@PathVariable Long id) {
        service.deleteById(id);

        return Response.sendResponse(new ResponseResult<>(200, "Até mais, obrigado pelos peixes!"));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<UserDto>> findById(@PathVariable Long id) {
        return Response.sendResponse(new ResponseResult<>(200, service.findById(id)));
    }

    @PostMapping("login")
    public ResponseEntity<Response<Map<String, Object>>> findByEmail(@RequestBody Users users) {
        return Response.sendResponse(new ResponseResult<>(200, service.login(users)));
    }
}
