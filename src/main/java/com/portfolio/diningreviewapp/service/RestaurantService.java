package com.portfolio.diningreviewapp.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.dto.SubmitRestaurantRequest;
import com.portfolio.diningreviewapp.model.enums.Allergy;
import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.repository.RestaurantRepository;
import com.portfolio.diningreviewapp.service.utils.ServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantService {
    
    private final RestaurantRepository repository;

    public Restaurant submitRestaurant(SubmitRestaurantRequest request) {
        Optional<Restaurant> restaurantOptional = repository.findByNameAndZipcode(request.getName(), request.getZipcode());

        if (restaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant with that name and zipcode exists.");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        String zipcode = ServiceUtil.validateZipcode(request.getZipcode());
        restaurant.setZipcode(zipcode);

        this.repository.save(restaurant);
        return restaurant;
    }

    public Restaurant getRestaurantById(Long id) {

        Optional<Restaurant> restaurantOptional = repository.findById(id);
        return restaurantOptional.orElse(null);

    }

    public List<Restaurant> getRestaurantsByZipcodeAndAllergy(String zipcode, Allergy allergy) {

        //TODO validate zipcode

        return switch (allergy.name()) {
            case "PEANUT" -> repository.findByZipcodeAndPeanutScoreNotNull(zipcode);
            case "EGG" -> repository.findByZipcodeAndEggScoreNotNull(zipcode);
            case "DAIRY" -> repository.findByZipcodeAndDairyScoreNotNull(zipcode);
            default -> new ArrayList<>();
        };
    }
}
