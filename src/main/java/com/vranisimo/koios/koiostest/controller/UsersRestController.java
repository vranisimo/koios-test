package com.vranisimo.koios.koiostest.controller;

import com.vranisimo.koios.koiostest.model.User;
import com.vranisimo.koios.koiostest.model.paging.Page;
import com.vranisimo.koios.koiostest.model.paging.PagingRequest;
import com.vranisimo.koios.koiostest.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersRestController {

    private final UsersService usersService;

    @Autowired
    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public Page<User> list(@RequestBody PagingRequest pagingRequest) {
        return usersService.getUsers(pagingRequest);
    }
}