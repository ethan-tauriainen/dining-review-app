package com.portfolio.diningreviewapp.service;

import java.util.List;
import java.util.Optional;

import com.portfolio.diningreviewapp.model.*;
import com.portfolio.diningreviewapp.model.dto.DiningReviewDto;
import com.portfolio.diningreviewapp.model.enums.Status;
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
    
    public DiningReview submitDiningReview(String displayName, DiningReviewDto dto) {

        // Only registered users may submit a review.
        Optional<User> userOptional = userRepository.findByDisplayName(displayName);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        }

        // convert dto to DiningReview
        //TODO enforce that scores are non-null
        DiningReview diningReview = new DiningReview();
        diningReview.setSubmittedBy(displayName);
        diningReview.setRestaurantId(dto.getRestaurantId());
        diningReview.setPeanutScore(dto.getPeanutScore());
        diningReview.setEggScore(dto.getEggScore());
        diningReview.setDairyScore(dto.getDairyScore());
        diningReview.setCommentary(dto.getCommentary());
        diningReview.setStatus(Status.PENDING);
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
        updateScores(diningReviewToUpdate.getRestaurantId());
        return diningReviewToUpdate;
    }

    public Restaurant updateScores(Long restaurantId) {

        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant does not exist.");
        }

        double avgPeanutScore;
        double avgEggScore;
        double avgDairyScore;
        double totalScore;

        List<DiningReview> approvedDiningReviews = this.diningReviewRepository.findAllByRestaurantIdAndStatus(
                restaurantId, Status.ACCEPTED
        );

        double sumPeanutScore = 0, sumEggScore = 0, sumDairyScore = 0;
        for (DiningReview diningReview : approvedDiningReviews) {
            sumPeanutScore += diningReview.getPeanutScore();
            sumEggScore += diningReview.getEggScore();
            sumDairyScore += diningReview.getDairyScore();
        }

        avgPeanutScore = Math.round((sumPeanutScore / approvedDiningReviews.size()) * 100) / 100D;
        avgEggScore = Math.round((sumEggScore / approvedDiningReviews.size()) * 100) / 100D;
        avgDairyScore = Math.round((sumDairyScore / approvedDiningReviews.size()) * 100) / 100D;

        double sumAvgScores = avgPeanutScore + avgEggScore + avgDairyScore;
        totalScore = Math.round((sumAvgScores / 3) * 100) / 100D;

        Restaurant restaurantToUpdate = restaurantOptional.get();
        restaurantToUpdate.setPeanutScore(avgPeanutScore);
        restaurantToUpdate.setEggScore(avgEggScore);
        restaurantToUpdate.setDairyScore(avgDairyScore);
        restaurantToUpdate.setOverallScore(totalScore);
        this.restaurantRepository.save(restaurantToUpdate);
        return restaurantToUpdate;
    }
}
