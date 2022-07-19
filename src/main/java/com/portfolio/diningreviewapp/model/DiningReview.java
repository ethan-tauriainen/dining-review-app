package com.portfolio.diningreviewapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_DINING_REVIEWS")
@Data
public class DiningReview {
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    protected Long id;

    @Column(name = "SUBMITTED_BY")
    private String submittedBy;
    @Column(name = "RESTAURANT_ID")
    private Long restaurantId;
    @Column(name = "PEANUT_SCORE")
    private Double peanutScore;
    @Column(name = "EGG_SCORE")
    private Double eggScore;
    @Column(name = "DAIRY_SCORE")
    private Double dairyScore;
    @Column(name = "COMMENTARY")
    private String commentary;
    
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;
}
