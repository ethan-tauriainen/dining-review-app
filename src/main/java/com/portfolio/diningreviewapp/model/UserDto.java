package com.portfolio.diningreviewapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("zipcode")
    private String zipcode;
    @JsonProperty("isPeanut")
    private Boolean isPeanut;
    @JsonProperty("isEgg")
    private Boolean isEgg;
    @JsonProperty("isDairy")
    private Boolean isDairy;
}
