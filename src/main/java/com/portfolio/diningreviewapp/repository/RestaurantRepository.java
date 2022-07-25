package com.portfolio.diningreviewapp.repository;

import java.util.List;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM TB_RESTAURANTS WHERE ZIPCODE = ?1 AND PEANUT_SCORE IS NOT NULL ORDER BY PEANUT_SCORE DESC", nativeQuery = true)
    List<Restaurant> findByZipcodeAndPeanutScoreNotNull(String zipcode);
    @Query(value = "SELECT * FROM TB_RESTAURANTS WHERE ZIPCODE = ?1 AND EGG_SCORE IS NOT NULL ORDER BY EGG_SCORE DESC", nativeQuery = true)
    List<Restaurant> findByZipcodeAndEggScoreNotNull(String zipcode);
    @Query(value = "SELECT * FROM TB_RESTAURANTS WHERE ZIPCODE = ?1 AND DAIRY_SCORE IS NOT NULL ORDER BY DAIRY_SCORE DESC", nativeQuery = true)
    List<Restaurant> findByZipcodeAndDairyScoreNotNull(String zipcode);
    @Query(value = "SELECT ID, DAIRY_SCORE, EGG_SCORE, NAME, OVERALL_SCORE, PEANUT_SCORE, ZIPCODE FROM TB_RESTAURANTS WHERE NAME = ?1 AND ZIPCODE = ?2", nativeQuery = true)
    Optional<Restaurant> findByNameAndZipcode(String name, String zipcode);
}
