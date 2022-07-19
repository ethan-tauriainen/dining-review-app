package com.portfolio.diningreviewapp.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.Allergy;
import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantService {
    
    private final RestaurantRepository repository;

    public Restaurant submitRestaurant(Restaurant restaurant) {
        Optional<Restaurant> restaurantOptional = repository.findByNameAndZipcode(restaurant.getZipcode(), restaurant.getName());

        if (restaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant with that name and zipcode exists.");
        }

        return repository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {

        Optional<Restaurant> restaurantOptional = repository.findById(id);
        return restaurantOptional.orElse(null);

    }

    public List<Restaurant> getRestaurantsByZipcodeAndAllergy(String zipcode, Allergy allergy) {

        return switch (allergy.name()) {
            case "PEANUT" -> repository.findByZipcodeAndPeanutScoreNotNull(zipcode);
            case "EGG" -> repository.findByZipcodeAndEggScoreNotNull(zipcode);
            case "DAIRY" -> repository.findByZipcodeAndDairyScoreNotNull(zipcode);
            default -> new ArrayList<>();
        };
    }
}