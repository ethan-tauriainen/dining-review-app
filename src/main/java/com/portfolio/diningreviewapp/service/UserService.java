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
        repository.save(user);
        return user;
    }

    public User updateUser(UserDto userDto) {

        Optional<User> userOptional = this.repository.findByDisplayName(userDto.getDisplayName());

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        }

        User userToUpdate = userOptional.get();

        if (userDto.getCity() != null) {
            userToUpdate.setCity(userDto.getCity());
        }

        if (userDto.getState() != null) {
            userToUpdate.setState(userDto.getState());
        }

        if (userDto.getZipcode() != null) {
            userToUpdate.setZipcode(userDto.getZipcode());
        }

        if (userDto.getIsPeanut() != null) {
            userToUpdate.setIsPeanut(userDto.getIsPeanut());
        }

        if (userDto.getIsEgg() != null) {
            userToUpdate.setIsEgg(userDto.getIsEgg());
        }

        if (userDto.getIsDairy() != null) {
            userToUpdate.setIsDairy(userDto.getIsDairy());
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

        // Validate zipcode
        String zipcode = dto.getZipcode();
        Pattern pattern = Pattern.compile("\\b\\d{5}\\b");
        Matcher matcher = pattern.matcher(zipcode);

        Pattern pattern1 = Pattern.compile("\\b\\d{5}-\\d{4}\\b");
        Matcher matcher1 = pattern1.matcher(zipcode);

        if (matcher.matches() || matcher1.matches()) {
            user.setZipcode(zipcode);
        }

        // If the value is null from the DTO, then set it to false
        boolean isPeanut = dto.getIsPeanut() != null && dto.getIsPeanut();
        user.setIsPeanut(isPeanut);

        boolean isEgg = dto.getIsEgg() != null && dto.getIsEgg();
        user.setIsEgg(isEgg);

        boolean isDairy = dto.getIsDairy() != null && dto.getIsDairy();
        user.setIsDairy(isDairy);

        return user;
    }
}
