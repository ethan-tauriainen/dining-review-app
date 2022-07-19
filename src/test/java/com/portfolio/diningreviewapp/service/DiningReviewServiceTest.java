package com.portfolio.diningreviewapp.service;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.diningreviewapp.model.DiningReview;
import com.portfolio.diningreviewapp.model.Restaurant;
import com.portfolio.diningreviewapp.model.Status;
import com.portfolio.diningreviewapp.model.User;
import com.portfolio.diningreviewapp.repository.DiningReviewRepository;
import com.portfolio.diningreviewapp.repository.RestaurantRepository;
import com.portfolio.diningreviewapp.repository.UserRepository;

public class DiningReviewServiceTest {

    private DiningReviewRepository diningReviewRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private DiningReviewService service;

    @BeforeEach
    void setup() {

        diningReviewRepository = Mockito.mock(DiningReviewRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        service = new DiningReviewService(diningReviewRepository, userRepository, restaurantRepository);
    }

    @Test
    void submitDiningReview_success() {

        User user = new User();
        user.setDisplayName("Ethan");
        user.setCity("Plymouth");
        user.setState("Michigan");
        user.setZipcode("48170");
        user.setIsPeanut(false);
        user.setIsEgg(false);
        user.setIsDairy(true);

        DiningReview diningReview = new DiningReview();
        diningReview.setSubmittedBy("Ethan");
        diningReview.setRestaurantId(1L);
        diningReview.setPeanutScore(4.5D);
        diningReview.setEggScore(5D);
        diningReview.setDairyScore(2.3);
        diningReview.setCommentary("Overall an excellent place to eat.");
        
        Mockito.when(userRepository.findByDisplayName(user.getDisplayName())).thenReturn(Optional.of(user));
        Mockito.when(diningReviewRepository.save(diningReview)).thenReturn(diningReview);
        DiningReview submittedReview = service.submitDiningReview(user.getDisplayName(), diningReview);

        Assertions.assertNotNull(submittedReview);
        Assertions.assertEquals(submittedReview.getSubmittedBy(), diningReview.getSubmittedBy());
    }

    @Test
    void submitDiningReview_noUser_failure() {

        String displayName = "Ethan";

        DiningReview diningReview = new DiningReview();
        diningReview.setSubmittedBy("Ethan");
        diningReview.setRestaurantId(1L);
        diningReview.setPeanutScore(4.5D);
        diningReview.setEggScore(5D);
        diningReview.setDairyScore(2.3);
        diningReview.setCommentary("Overall an excellent place to eat.");

        Mockito.when(userRepository.findByDisplayName(displayName)).thenReturn(Optional.empty());
        Mockito.when(diningReviewRepository.save(diningReview)).thenReturn(diningReview);

        Assertions.assertThrows(ResponseStatusException.class, () -> service.submitDiningReview(displayName, diningReview));
    }

    @Test
    void getPendingDiningReviews_success() {

        DiningReview diningReview = new DiningReview();
        diningReview.setSubmittedBy("Alice");
        diningReview.setStatus(Status.PENDING);

        DiningReview diningReview2 = new DiningReview();
        diningReview2.setSubmittedBy("Bob");
        diningReview2.setStatus(Status.PENDING);

        List<DiningReview> diningReviewList = new ArrayList<>();
        diningReviewList.add(diningReview);
        diningReviewList.add(diningReview2);

        Mockito.when(diningReviewRepository.findByStatus(Status.PENDING)).thenReturn(diningReviewList);
        List<DiningReview> resultList = service.getPendingDiningReviews();

        Assertions.assertEquals(diningReviewList.get(0).getSubmittedBy(), resultList.get(0).getSubmittedBy());
        Assertions.assertEquals(diningReviewList.get(1).getSubmittedBy(), resultList.get(1).getSubmittedBy());
    }

    @Test
    void updateDiningReviewStatus_success() {

        Long id = 1L;

        DiningReview diningReview = new DiningReview();
        diningReview.setId(id);
        diningReview.setStatus(Status.PENDING);

        Mockito.when(diningReviewRepository.findById(id)).thenReturn(Optional.of(diningReview));
        Mockito.when(diningReviewRepository.save(any())).thenReturn(any());
        DiningReview updatedReview = service.updateDiningReviewStatus(id, Status.ACCEPTED);

        Status expected = Status.ACCEPTED;
        Status actual = updatedReview.getStatus();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateDiningReviewStatus_reviewDoesNotExist_failure() {

        Long id = 1L;

        Mockito.when(diningReviewRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateDiningReviewStatus(id, Status.ACCEPTED));
    }

    @Test
    void getApprovedDiningReviews_success() {

        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        DiningReview diningReview = new DiningReview();
        diningReview.setSubmittedBy("Alice");
        diningReview.setRestaurantId(restaurantId);
        diningReview.setStatus(Status.ACCEPTED);

        DiningReview diningReview2 = new DiningReview();
        diningReview2.setSubmittedBy("Bob");
        diningReview2.setRestaurantId(restaurantId);
        diningReview2.setStatus(Status.ACCEPTED);

        List<DiningReview> diningReviewList = new ArrayList<>();
        diningReviewList.add(diningReview);
        diningReviewList.add(diningReview2);

        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Mockito.when(diningReviewRepository.findAllByRestaurantIdAndStatus(restaurantId, Status.ACCEPTED)).thenReturn(diningReviewList);
        List<DiningReview> resultList = service.getApprovedDiningReviews(restaurantId);

        Assertions.assertEquals(diningReviewList.get(0).getSubmittedBy(), resultList.get(0).getSubmittedBy());
        Assertions.assertEquals(diningReviewList.get(1).getSubmittedBy(), resultList.get(1).getSubmittedBy());
    }

    @Test
    void getApprovedDiningReviews_noRestaurant_failure() {

        Long restaurantId = 1L;

        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> service.getApprovedDiningReviews(restaurantId));
    }
}
