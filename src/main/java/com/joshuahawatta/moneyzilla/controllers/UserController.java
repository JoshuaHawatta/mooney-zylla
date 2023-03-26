package com.joshuahawatta.moneyzilla.controllers;

import com.joshuahawatta.moneyzilla.dtos.user.UserDto;
import com.joshuahawatta.moneyzilla.entities.responses.Response;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResult;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResultWIthMessage;
import com.joshuahawatta.moneyzilla.models.User;
import com.joshuahawatta.moneyzilla.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<Response<List<UserDto>>> findAll() {
        return Response.sendResponse(new ResponseResult<>(200, service.findAll()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<UserDto>> findById(@PathVariable Long id) {
        return Response.sendResponse(new ResponseResult<>(200, service.findById(id)));
    }

    @PostMapping(value = "login")
    public ResponseEntity<Response<UserDto>> findByEmail(@RequestBody User user) {
        return Response.sendResponse(new ResponseResult<>(200, service.login(user)));
    }

    @PostMapping(value = "register")
    public ResponseEntity<Response<UserDto>> save(@RequestBody User user) {
        UserDto newUser = service.save(user);

        return Response.sendResponse(
            new ResponseResultWIthMessage<>(201, newUser, "Olá, " + newUser.getName() + "!")
        );
    }

}
