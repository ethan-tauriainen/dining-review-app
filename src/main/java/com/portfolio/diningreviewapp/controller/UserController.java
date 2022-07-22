package com.portfolio.diningreviewapp.controller;

import com.portfolio.diningreviewapp.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {

        return new ResponseEntity<UserDto>(HttpStatus.CREATED);
    }
}
