package com.portfolio.diningreviewapp.service;

import java.util.Optional;

import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.model.dto.UserDto;
import com.portfolio.diningreviewapp.repository.UserRepository;
import com.portfolio.diningreviewapp.service.utils.ServiceUtil;
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
        this.repository.save(user);
        return user;
    }

    public User updateUser(UserDto userDto, String displayName) {

        Optional<User> userOptional = this.repository.findByDisplayName(displayName);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        }

        User userToUpdate = userOptional.get();

        if (userDto.getCity() != null && !userDto.getCity().isEmpty()) {
            userToUpdate.setCity(userDto.getCity());
        }

        if (userDto.getState() != null && !userDto.getState().isEmpty()) {
            userToUpdate.setState(userDto.getState());
        }

        if (userDto.getZipcode() != null && !userDto.getZipcode().isEmpty()) {
            String zipcode = ServiceUtil.validateZipcode(userDto.getZipcode());
            userToUpdate.setZipcode(zipcode);
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

        String zipcode = ServiceUtil.validateZipcode(dto.getZipcode());
        user.setZipcode(zipcode);

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
