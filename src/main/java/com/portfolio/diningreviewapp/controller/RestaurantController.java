package com.portfolio.diningreviewapp.controller;

import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.model.dto.RestaurantResponse;
import com.portfolio.diningreviewapp.model.dto.SubmitRestaurantRequest;
import com.portfolio.diningreviewapp.model.enums.Allergy;
import com.portfolio.diningreviewapp.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> submitRestaurant(
            @RequestBody SubmitRestaurantRequest request) {

        try {
            Restaurant restaurantFromBackend = this.restaurantService.submitRestaurant(request);
            RestaurantResponse response = this.convertToResponse(restaurantFromBackend);
            return ResponseEntity.created(URI.create("/api/restaurant/" + response.getId())).body(response);
        } catch (ResponseStatusException e) {
            RestaurantResponse errorResponse = new RestaurantResponse();
            errorResponse.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") Long id) {

        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(this.convertToResponse(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByZipcodeAndAllergy(
            @RequestParam("zipcode") String zipcode,
            @RequestParam("allergy") String allergy) {

        boolean sentinel = false;
        for (Allergy a : Allergy.values()) {
            if (a.name().equalsIgnoreCase(allergy)) {
                sentinel = true;
                break;
            }
        }

        if (!sentinel) {
            RestaurantResponse errorResponse = new RestaurantResponse();
            errorResponse.setMsg("Invalid allergy.");
            List<RestaurantResponse> errorResponseList = new ArrayList<>();
            errorResponseList.add(errorResponse);
            return ResponseEntity.badRequest().body(errorResponseList);
        }

        List<Restaurant> restaurants = this.restaurantService.getRestaurantsByZipcodeAndAllergy(
                zipcode,
                Allergy.valueOf(allergy.toUpperCase())
        );

        List<RestaurantResponse> responses = new ArrayList<>();

        if (!restaurants.isEmpty()) {
            restaurants.forEach((restaurant) -> responses.add(this.convertToResponse(restaurant)));
        }

        return ResponseEntity.ok(responses);
    }

    private RestaurantResponse convertToResponse(Restaurant restaurant) {

        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setPeanutScore(restaurant.getPeanutScore());
        response.setEggScore(restaurant.getEggScore());
        response.setDairyScore(restaurant.getDairyScore());
        response.setOverallScore(restaurant.getOverallScore());
        response.setName(restaurant.getName());
        response.setZipcode(restaurant.getZipcode());
        return response;
    }
}
