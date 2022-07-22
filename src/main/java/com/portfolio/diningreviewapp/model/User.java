package com.portfolio.diningreviewapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME")
    private String displayName;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP")
    private String zipcode;

    @Column(name = "PEANUT_ALLERGY")
    private Boolean isPeanut;
    @Column(name = "EGG_ALLERGY")
    private Boolean isEgg;
    @Column(name = "DAIRY_ALLERGY")
    private Boolean isDairy;
}
