package com.portfolio.diningreviewapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiningReviewDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("submittedBy")
    private String submittedBy;
    @JsonProperty("restaurantId")
    private Long restaurantId;
    @JsonProperty("peanutScore")
    private Double peanutScore;
    @JsonProperty("eggScore")
    private Double eggScore;
    @JsonProperty("dairyScore")
    private Double dairyScore;
    @JsonProperty("commentary")
    private String commentary;
    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private String msg;
}
