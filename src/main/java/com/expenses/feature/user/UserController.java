package com.expenses.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDTO userCreateDTO) {
        this.userService.createUser(userCreateDTO);
    }
}
