package com.erisnilton.controle.controller;

import com.erisnilton.controle.model.User;
import com.erisnilton.controle.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public record UserController(UserService service) {

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

}

