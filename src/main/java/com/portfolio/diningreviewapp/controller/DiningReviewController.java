package com.portfolio.diningreviewapp.controller;

import com.portfolio.diningreviewapp.model.DiningReview;
import com.portfolio.diningreviewapp.model.DiningReviewDto;
import com.portfolio.diningreviewapp.service.DiningReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/review")
public class DiningReviewController {

    private final DiningReviewService diningReviewService;

    @PostMapping
    public ResponseEntity<DiningReviewDto> submitDiningReview(@RequestBody DiningReviewDto dto) {
        try {
            DiningReview diningReview = this.diningReviewService.submitDiningReview(dto.getSubmittedBy(), dto);
            DiningReviewDto response = convertToDto(diningReview);
            return ResponseEntity.created(URI.create("/api/review/" + response.getId())).body(response);
        } catch (ResponseStatusException e) {
            DiningReviewDto errorResponse = new DiningReviewDto();
            errorResponse.setStatus(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private DiningReviewDto convertToDto(DiningReview diningReview) {

        DiningReviewDto dto = new DiningReviewDto();
        dto.setId(diningReview.getId());
        dto.setSubmittedBy(diningReview.getSubmittedBy());
        dto.setRestaurantId(diningReview.getRestaurantId());
        dto.setPeanutScore(diningReview.getPeanutScore());
        dto.setEggScore(diningReview.getEggScore());
        dto.setDairyScore(diningReview.getDairyScore());
        dto.setCommentary(diningReview.getCommentary());
        dto.setStatus(diningReview.getStatus().name());
        return dto;
    }
}
