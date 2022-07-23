package com.portfolio.diningreviewapp.controller;

import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.model.UserDto;
import com.portfolio.diningreviewapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {

        try {
            User createdUser = this.service.createUser(dto);
            UserDto response = convertToDto(createdUser);
            return ResponseEntity.created(URI.create("/api/user/" + response.getDisplayName())).body(response);
        } catch (ResponseStatusException e) {
            return getErrorMessage(e.getMessage());
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto, @PathVariable("name") String displayName) {

        try {
            User updatedUser = this.service.updateUser(dto, displayName);
            UserDto response = convertToDto(updatedUser);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return getErrorMessage(e.getMessage());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable("name") String displayName) {

        User userFromBackend = this.service.getUser(displayName);

        if (userFromBackend == null) {
            return getErrorMessage("User not found.");
        }

        UserDto response = convertToDto(userFromBackend);
        return ResponseEntity.ok(response);
    }

    private UserDto convertToDto(User user) {

        UserDto dto = new UserDto();
        dto.setDisplayName(user.getDisplayName());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setZipcode(user.getZipcode());
        dto.setIsPeanut(user.getIsPeanut());
        dto.setIsEgg(user.getIsEgg());
        dto.setIsDairy(user.getIsDairy());
        return dto;
    }

    private ResponseEntity<UserDto> getErrorMessage(String msg) {

        UserDto errorDto = new UserDto();
        errorDto.setMsg(msg);
        return ResponseEntity.badRequest().body(errorDto);
    }
}
