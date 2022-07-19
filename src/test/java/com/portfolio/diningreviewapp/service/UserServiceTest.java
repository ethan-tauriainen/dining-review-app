package com.portfolio.diningreviewapp.service;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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

        User user = new User();
        user.setDisplayName("Ethan");
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        Mockito.when(repository.save(user)).thenReturn(user);
        User createdUser = service.createUser(user);

        Mockito.verify(repository, times(1)).save(user);

        Assertions.assertNotNull(createdUser, "The user was created.");
        Assertions.assertEquals(user.getDisplayName(), createdUser.getDisplayName());
    }

    @Test
    void createUser_userExists_failure() {

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

        Assertions.assertThrows(ResponseStatusException.class, () -> service.createUser(user));
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

        User userWithUpdates = new User();
        userWithUpdates.setDisplayName(displayName);
        userWithUpdates.setCity(null);
        userWithUpdates.setState(null);
        userWithUpdates.setZipcode(null);
        userWithUpdates.setIsPeanut(null);
        userWithUpdates.setIsEgg(null);
        userWithUpdates.setIsDairy(false);  // Updated property.

        Mockito.when(repository.save(any())).thenReturn(any());
        User updatedUser = service.updateUser(userWithUpdates);

        Assertions.assertEquals(displayName, updatedUser.getDisplayName());
        Assertions.assertEquals(userFromBackend.getCity(), updatedUser.getCity());
        Assertions.assertEquals(userFromBackend.getState(), updatedUser.getState());
        Assertions.assertEquals(userFromBackend.getZipcode(), updatedUser.getZipcode());
        Assertions.assertEquals(userFromBackend.getIsPeanut(), updatedUser.getIsPeanut());
        Assertions.assertEquals(userFromBackend.getIsEgg(), updatedUser.getIsEgg());

        Assertions.assertEquals(userWithUpdates.getIsDairy(), updatedUser.getIsDairy());
    }

    @Test
    void updateUser_userDoesNotExist_failure() {

        String displayName = "Ethan";

        User user = new User();
        user.setDisplayName(displayName);
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        Mockito.when(repository.findByDisplayName(displayName)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateUser(user));
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