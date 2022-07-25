package com.portfolio.diningreviewapp.repository;

import java.util.List;

import com.portfolio.diningreviewapp.model.DiningReview;
import com.portfolio.diningreviewapp.model.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findByStatus(Status status);
    List<DiningReview> findAllByRestaurantIdAndStatus(Long restaurantId, Status status);
}
