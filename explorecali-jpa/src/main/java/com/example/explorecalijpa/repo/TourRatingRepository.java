package com.example.explorecalijpa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.explorecalijpa.model.TourRating;

//It won't be exported as a REST resource because we don't want to expose it directly
@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends JpaRepository<TourRating, Integer> {

  //It will return all List of TourRating for a given tourId
  List<TourRating> findByTourId(Integer tourId);

  //It will return tour rating for a given tourId and customerId combination if exists
  Optional<TourRating> findByTourIdAndCustomerId(Integer tourId, Integer customerId);
}
