package com.portfolio.diningreviewapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.portfolio.diningreviewapp.model.dto.SubmitRestaurantRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.diningreviewapp.model.enums.Allergy;
import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.repository.RestaurantRepository;

public class RestaurantServiceTest {
    
    private RestaurantRepository repository;
    private RestaurantService service;

    @BeforeEach
    void setup() {

        repository = Mockito.mock(RestaurantRepository.class);
        service = new RestaurantService(repository);
    }

    @Test
    void submitRestaurant_success() {

        String name = "Antonio's Italian Cucina";
        String zipcode = "48170";

        SubmitRestaurantRequest request = new SubmitRestaurantRequest();
        request.setName(name);
        request.setZipcode(zipcode);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setZipcode(zipcode);

        Mockito.when(repository.findByNameAndZipcode(restaurant.getZipcode(), restaurant.getName())).thenReturn(Optional.empty());
        Mockito.when(repository.save(restaurant)).thenReturn(restaurant);
        Restaurant submittedRestaurant = service.submitRestaurant(request);

        Assertions.assertEquals(restaurant.getName(), submittedRestaurant.getName());
        Assertions.assertEquals(restaurant.getZipcode(), submittedRestaurant.getZipcode());
    }

    @Test
    void submitRestaurant_alreadyExists_failure() {

        String name = "Antonio's Italian Cucina";
        String zipcode = "48170";

        SubmitRestaurantRequest request = new SubmitRestaurantRequest();
        request.setName(name);
        request.setZipcode(zipcode);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setZipcode(zipcode);

        Mockito.when(repository.findByNameAndZipcode(restaurant.getZipcode(), restaurant.getName())).thenReturn(Optional.of(restaurant));
        
        Assertions.assertThrows(ResponseStatusException.class, () -> service.submitRestaurant(request));
    }

    @Test
    void getRestaurantById_success() {

        Long id = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Antonio's Italien Cucina");
        restaurant.setZipcode("48170");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(restaurant));
        Restaurant restaurantFromBackend = service.getRestaurantById(id);

        Assertions.assertEquals(restaurant.getId(), restaurantFromBackend.getId());
        Assertions.assertEquals(restaurant.getName(), restaurantFromBackend.getName());
        Assertions.assertEquals(restaurant.getZipcode(), restaurantFromBackend.getZipcode());
    }

    @Test
    void getRestaurantById_noRestaurant_returnsNull() {

        Long id = 1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Restaurant restaurantFromBackend = service.getRestaurantById(id);

        Assertions.assertNull(restaurantFromBackend);
    }

    @Test
    void getRestaurantsByZipcodeAndAllergy_peanut_success() {

        Allergy allergy = Allergy.PEANUT;
        String zipcode = "48170";

        Mockito.when(repository.findByZipcodeAndPeanutScoreNotNull(any())).thenReturn(any());
        service.getRestaurantsByZipcodeAndAllergy(zipcode, allergy);

        Mockito.verify(repository, times(0)).findByZipcodeAndEggScoreNotNull(zipcode);
        Mockito.verify(repository, times(0)).findByZipcodeAndDairyScoreNotNull(zipcode);
        Mockito.verify(repository, times(1)).findByZipcodeAndPeanutScoreNotNull(zipcode);
    }

    @Test
    void getRestaurantsByZipcodeAndAllergy_egg_success() {

        Allergy allergy = Allergy.EGG;
        String zipcode = "48170";

        Mockito.when(repository.findByZipcodeAndPeanutScoreNotNull(any())).thenReturn(any());
        service.getRestaurantsByZipcodeAndAllergy(zipcode, allergy);

        Mockito.verify(repository, times(1)).findByZipcodeAndEggScoreNotNull(zipcode);
        Mockito.verify(repository, times(0)).findByZipcodeAndDairyScoreNotNull(zipcode);
        Mockito.verify(repository, times(0)).findByZipcodeAndPeanutScoreNotNull(zipcode);
    }

    @Test
    void getRestaurantsByZipcodeAndAllergy_dairy_success() {

        Allergy allergy = Allergy.DAIRY;
        String zipcode = "48170";

        Mockito.when(repository.findByZipcodeAndPeanutScoreNotNull(any())).thenReturn(any());
        service.getRestaurantsByZipcodeAndAllergy(zipcode, allergy);

        Mockito.verify(repository, times(0)).findByZipcodeAndEggScoreNotNull(zipcode);
        Mockito.verify(repository, times(1)).findByZipcodeAndDairyScoreNotNull(zipcode);
        Mockito.verify(repository, times(0)).findByZipcodeAndPeanutScoreNotNull(zipcode);
    }
}
