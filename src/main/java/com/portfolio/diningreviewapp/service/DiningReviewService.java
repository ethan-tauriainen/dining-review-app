package com.portfolio.diningreviewapp.service;

import java.util.List;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.DiningReview;
import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.model.Status;
import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.repository.DiningReviewRepository;
import com.portfolio.diningreviewapp.repository.RestaurantRepository;
import com.portfolio.diningreviewapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DiningReviewService {
    
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    
    public DiningReview submitDiningReview(String displayName, DiningReview diningReview) {

        // Only registered users may submit a review.
        Optional<User> userOptional = userRepository.findByDisplayName(displayName);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        }

        return diningReviewRepository.save(diningReview);
    }

    public List<DiningReview> getPendingDiningReviews() {
        Status status = Status.PENDING;
        return diningReviewRepository.findByStatus(status);
    }

    public DiningReview updateDiningReviewStatus(Long diningReviewId, Status status) {
        Optional<DiningReview> diningReviewOptional = diningReviewRepository.findById(diningReviewId);

        if (diningReviewOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review does not exist.");
        }

        DiningReview diningReviewToUpdate = diningReviewOptional.get();
        diningReviewToUpdate.setStatus(status);
        diningReviewRepository.save(diningReviewToUpdate);
        return diningReviewToUpdate;
    }

    public List<DiningReview> getApprovedDiningReviews(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant does not exist.");
        }

        return diningReviewRepository.findAllByRestaurantIdAndStatus(restaurantId, Status.ACCEPTED);
    }
}
