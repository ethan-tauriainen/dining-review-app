package com.portfolio.diningreviewapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_RESTAURANTS")
@Data
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected Long id;

    @Column(name = "PEANUT_SCORE")
    private Double peanutScore;
    @Column(name = "EGG_SCORE")
    private Double eggScore;
    @Column(name = "DAIRY_SCORE")
    private Double dairyScore;
    @Column(name = "OVERALL_SCORE")
    private Double overallScore;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ZIPCODE")
    private String zipcode;
}
