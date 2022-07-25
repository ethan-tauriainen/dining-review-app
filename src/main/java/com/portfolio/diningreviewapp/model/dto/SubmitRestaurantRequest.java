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
public class SubmitRestaurantRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("zipcode")
    private String zipcode;
}
