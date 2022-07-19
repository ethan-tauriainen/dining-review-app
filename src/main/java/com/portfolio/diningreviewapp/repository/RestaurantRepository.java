package com.portfolio.diningreviewapp.repository;

import java.util.List;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findByZipcodeAndPeanutScoreNotNull(String zipcode);
    List<Restaurant> findByZipcodeAndEggScoreNotNull(String zipcode);
    List<Restaurant> findByZipcodeAndDairyScoreNotNull(String zipcode);
    Optional<Restaurant> findByNameAndZipcode(String zipcode, String name);
}
