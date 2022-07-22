package com.portfolio.diningreviewapp.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.model.UserDto;
import com.portfolio.diningreviewapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    
    private final UserRepository repository;

    public User createUser(UserDto userDto) {

        User userFromBackend = this.getUser(userDto.getDisplayName());

        if (userFromBackend != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
        }

        User user = this.convertToUser(userDto);
        return repository.save(user);
    }

    public User updateUser(User user) {

        Optional<User> userOptional = this.repository.findByDisplayName(user.getDisplayName());

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        }

        User userToUpdate = userOptional.get();

        if (user.getCity() != null) {
            userToUpdate.setCity(user.getCity());
        }

        if (user.getState() != null) {
            userToUpdate.setState(user.getState());
        }

        if (user.getZipcode() != null) {
            userToUpdate.setZipcode(user.getZipcode());
        }

        if (user.getIsPeanut() != null) {
            userToUpdate.setIsPeanut(user.getIsPeanut());
        }

        if (user.getIsEgg() != null) {
            userToUpdate.setIsEgg(user.getIsEgg());
        }

        if (user.getIsDairy() != null) {
            userToUpdate.setIsDairy(user.getIsDairy());
        }

        this.repository.save(userToUpdate);
        return userToUpdate;
    }

    public User getUser(String displayName) {

        Optional<User> userOptional = repository.findByDisplayName(displayName);
        return userOptional.orElse(null);
    }

    private User convertToUser(UserDto dto) {

        User user = new User();
        user.setDisplayName(dto.getDisplayName());
        user.setCity(dto.getCity());
        user.setState(dto.getState());

        // validate zipcode
        String zipcode = dto.getZipcode();
        Pattern pattern = Pattern.compile("\\b\\d{5}\\b");
        Matcher matcher = pattern.matcher(zipcode);

        Pattern pattern1 = Pattern.compile("\\b\\d{5}-\\d{4}\\b");
        Matcher matcher1 = pattern1.matcher(zipcode);

        if (matcher.matches() || matcher1.matches()) {
            user.setZipcode(zipcode);
        }

        user.setIsPeanut(dto.getIsPeanut());
        user.setIsEgg(dto.getIsEgg());
        user.setIsDairy(dto.getIsDairy());

        return user;
    }
}
