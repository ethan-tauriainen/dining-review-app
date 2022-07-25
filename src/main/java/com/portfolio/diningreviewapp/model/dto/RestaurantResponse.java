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
public class RestaurantResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("peanutScore")
    private Double peanutScore;
    @JsonProperty("eggScore")
    private Double eggScore;
    @JsonProperty("dairyScore")
    private Double dairyScore;
    @JsonProperty("overallScore")
    private Double overallScore;
    @JsonProperty("name")
    private String name;
    @JsonProperty("zipcode")
    private String zipcode;

    @JsonProperty("error")
    private String msg;
}
