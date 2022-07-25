package com.portfolio.diningreviewapp.controller;

import com.portfolio.diningreviewapp.model.DiningReview;
import com.portfolio.diningreviewapp.model.dto.DiningReviewDto;
import com.portfolio.diningreviewapp.model.dto.StatusDto;
import com.portfolio.diningreviewapp.model.enums.Status;
import com.portfolio.diningreviewapp.service.DiningReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/admin/pending")
    public ResponseEntity<List<DiningReviewDto>> getPendingDiningReviews() {

        List<DiningReview> pendingDiningReviews = this.diningReviewService.getPendingDiningReviews();
        List<DiningReviewDto> pendingDiningReviewDtoList = new ArrayList<>();
        pendingDiningReviews.forEach(pendingDiningReview -> pendingDiningReviewDtoList.add(convertToDto(pendingDiningReview)));
        return ResponseEntity.ok(pendingDiningReviewDtoList);
    }

    @PutMapping("/admin/id/{id}/status/{status}")
    public ResponseEntity<DiningReviewDto> updateDiningReviewStatus(@PathVariable("id") Long id,
                                                                    @RequestBody StatusDto statusDto) {
        try {
            // verify status provided
            boolean sentinel = false;
            for (Status s : Status.values()) {
                if (s.name().equalsIgnoreCase(statusDto.getStatus())) {
                    sentinel = true;
                    break;
                }
            }

            if (!sentinel) {
                DiningReviewDto errorResponse = new DiningReviewDto();
                errorResponse.setMsg("Invalid status.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            DiningReview updatedDiningReview = this.diningReviewService.updateDiningReviewStatus(
                    id, Status.valueOf(statusDto.getStatus().toUpperCase())
            );

            DiningReviewDto response = convertToDto(updatedDiningReview);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            DiningReviewDto errorResponse = new DiningReviewDto();
            errorResponse.setMsg(e.getMessage());
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
