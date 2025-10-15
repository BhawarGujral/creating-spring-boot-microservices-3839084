package com.example.explorecalijpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tour_rating")
@Data
public class TourRating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @Column(name = "customer_id")
  private Integer customerId;

  @Column(nullable = false)
  private Integer score;

  @Column
  private String comment;

  protected TourRating() {
  }

  public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
    this.tour = tour;
    this.customerId = customerId;
    this.score = score;
    this.comment = comment;
  }

}