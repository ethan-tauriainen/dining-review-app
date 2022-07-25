package com.portfolio.diningreviewapp.service;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import com.portfolio.diningreviewapp.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.repository.UserRepository;

public class UserServiceTest {
    
    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setup() {

        repository = Mockito.mock(UserRepository.class);
        service = new UserService(repository);
    }
    
    @Test
    void createUser_success() {

        UserDto userDto = new UserDto();
        userDto.setDisplayName("Ethan");
        userDto.setCity("Plymouth");
        userDto.setState("Michigan");
        userDto.setZipcode("48170");
        userDto.setIsPeanut(false);
        userDto.setIsEgg(false);
        userDto.setIsDairy(true);

        User user = new User();
        user.setDisplayName("Ethan");
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        Mockito.when(repository.findByDisplayName(userDto.getDisplayName())).thenReturn(Optional.empty());
        Mockito.when(repository.save(user)).thenReturn(user);
        User createdUser = service.createUser(userDto);

        Mockito.verify(repository, times(1)).save(user);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(user.getDisplayName(), createdUser.getDisplayName());
        Assertions.assertEquals(user.getZipcode(), createdUser.getZipcode());
        Assertions.assertEquals(user.getCity(), createdUser.getCity());
        Assertions.assertEquals(user.getState(), createdUser.getState());
        Assertions.assertEquals(user.getIsPeanut(), createdUser.getIsPeanut());
        Assertions.assertEquals(user.getIsEgg(), createdUser.getIsEgg());
        Assertions.assertEquals(user.getIsDairy(), createdUser.getIsDairy());
    }

    @Test
    void createUser_userExists_failure() {

        String displayName = "Ethan";

        UserDto userDto = new UserDto();
        userDto.setDisplayName(displayName);
        userDto.setCity("Plymouth");
        userDto.setState("Michigan");
        userDto.setZipcode("48170");
        userDto.setIsPeanut(false);
        userDto.setIsEgg(false);
        userDto.setIsDairy(true);

        User user = new User();
        user.setDisplayName(displayName);
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.of(user));

        Assertions.assertThrows(ResponseStatusException.class, () -> service.createUser(userDto));
    }

    @Test
    void convertToUser_success_hyphenZip() {

        String displayName = "Ethan";
        String city = "Plymouth";
        String state = "Michigan";
        String zipcode = "48170-1234";

        UserDto dto = new UserDto();
        dto.setDisplayName(displayName);
        dto.setCity(city);
        dto.setState(state);
        dto.setZipcode(zipcode);

        User user = new User();
        user.setDisplayName(displayName);
        user.setCity(city);
        user.setState(state);
        user.setZipcode(zipcode);
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(false);

        Mockito.when(repository.findByDisplayName(dto.getDisplayName())).thenReturn(Optional.empty());
        Mockito.when(repository.save(user)).thenReturn(user);
        User createdUser = service.createUser(dto);

        Mockito.verify(repository, times(1)).save(user);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(user.getDisplayName(), createdUser.getDisplayName());
        Assertions.assertEquals(user.getZipcode(), createdUser.getZipcode());
        Assertions.assertEquals(user.getCity(), createdUser.getCity());
        Assertions.assertEquals(user.getState(), createdUser.getState());
        Assertions.assertEquals(user.getIsPeanut(), createdUser.getIsPeanut());
        Assertions.assertEquals(user.getIsEgg(), createdUser.getIsEgg());
        Assertions.assertEquals(user.getIsDairy(), createdUser.getIsDairy());
    }

    @Test
    void convertToUser_zipcodeDoesNotMatch_setToNull() {

        String displayName = "Ethan";
        String state = "Michigan";
        String zipcode = "4817";

        UserDto dto = new UserDto();
        dto.setDisplayName(displayName);
        dto.setState(state);
        dto.setZipcode(zipcode);

        User user = new User();
        user.setDisplayName(displayName);
        user.setCity(null);
        user.setState(state);
        user.setZipcode(null);
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(false);

        Mockito.when(repository.findByDisplayName(dto.getDisplayName())).thenReturn(Optional.empty());
        Mockito.when(repository.save(user)).thenReturn(user);
        User createdUser = service.createUser(dto);

        Mockito.verify(repository, times(1)).save(user);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(user.getDisplayName(), createdUser.getDisplayName());
        Assertions.assertEquals(user.getZipcode(), createdUser.getZipcode());
        Assertions.assertEquals(user.getCity(), createdUser.getCity());
        Assertions.assertEquals(user.getState(), createdUser.getState());
        Assertions.assertEquals(user.getIsPeanut(), createdUser.getIsPeanut());
        Assertions.assertEquals(user.getIsEgg(), createdUser.getIsEgg());
        Assertions.assertEquals(user.getIsDairy(), createdUser.getIsDairy());
    }

    @Test
    void updateUser_success() {

        String displayName = "Ethan";

        User userFromBackend = new User();
        userFromBackend.setDisplayName(displayName);
        userFromBackend.setCity("Plymouth");
        userFromBackend.setState("Michigan");
        userFromBackend.setZipcode("48170");
        userFromBackend.setIsPeanut(false);
        userFromBackend.setIsEgg(false);
        userFromBackend.setIsDairy(true);

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.of(userFromBackend));

        UserDto dto = new UserDto();
        dto.setDisplayName(displayName);
        dto.setIsDairy(false);

        Mockito.when(repository.save(any())).thenReturn(any());
        User updatedUser = service.updateUser(dto, displayName);

        Assertions.assertEquals(displayName, updatedUser.getDisplayName());
        Assertions.assertEquals(userFromBackend.getCity(), updatedUser.getCity());
        Assertions.assertEquals(userFromBackend.getState(), updatedUser.getState());
        Assertions.assertEquals(userFromBackend.getZipcode(), updatedUser.getZipcode());
        Assertions.assertEquals(userFromBackend.getIsPeanut(), updatedUser.getIsPeanut());
        Assertions.assertEquals(userFromBackend.getIsEgg(), updatedUser.getIsEgg());

        Assertions.assertEquals(dto.getIsDairy(), updatedUser.getIsDairy());
    }

    @Test
    void updateUser_userDoesNotExist_failure() {

        String displayName = "Ethan";

        UserDto dto = new UserDto();
        dto.setDisplayName(displayName);

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateUser(dto, displayName));
    }

    @Test
    void getUser_success() {

        String displayName = "Ethan";

        User user = new User();
        user.setDisplayName(displayName);
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.of(user));
        User userFromBackend = service.getUser(displayName);

        Assertions.assertEquals(user.getDisplayName(), userFromBackend.getDisplayName());
    }

    @Test
    void getUser_userDoesNotExist_returnsNull() {

        String displayName = "Ethan";

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.empty());
        User user = service.getUser(displayName);

        Assertions.assertNull(user);
    }
}
