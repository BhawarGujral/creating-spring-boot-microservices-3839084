package com.example.explorecalijpa.business;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.stereotype.Service;

import com.example.explorecalijpa.model.Tour;
import com.example.explorecalijpa.model.TourRating;
import com.example.explorecalijpa.repo.TourRatingRepository;
import com.example.explorecalijpa.repo.TourRepository;

@Service
public class TourRatingService {
  TourRatingRepository tourRatingRepository;
  TourRepository tourRepository;

  public TourRatingService(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
    this.tourRatingRepository = tourRatingRepository;
    this.tourRepository = tourRepository;
  }

  public TourRating createNew(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException {
    return tourRatingRepository.save(new TourRating(verifyTour(tourId), customerId, score, comment));
  }

  public Optional<TourRating> lookUpRatingById(int tourId){
    return tourRatingRepository.findById(tourId);
  }

  public List<TourRating> lookUpAll(){
    return tourRatingRepository.findAll();
  }

  public List<TourRating> lookUpRatings(int tourId) throws NoSuchElementException{
    return tourRatingRepository.findByTourId(verifyTour(tourId).getId());
  }

  public TourRating update(int tourId, Integer customerId, Integer score, String comment) throws NoSuchElementException{
    TourRating rating = verifyTourRating(tourId,customerId);
    rating.setScore(score);
    rating.setComment(comment);
    return tourRatingRepository.save(rating);
  }

  // Updating some values not whole rating
  public TourRating updateSome(int tourId, Integer customerId, Optional<Integer> score, Optional<String> comment) throws NoSuchElementException{
    TourRating rating = verifyTourRating(tourId,customerId);
    score.ifPresent(s -> rating.setScore(s));
    comment.ifPresent(c -> rating.setComment(c));
    return tourRatingRepository.save(rating);
  }

  public void delete(int tourId, Integer customerId) throws NoSuchElementException{
    TourRating rating = verifyTourRating(tourId, customerId);
    tourRatingRepository.delete(rating);
  }

  // Get the average score of tour
  public Double getAverageScore(int tourId) throws NoSuchElementException{
    List<TourRating> ratings = tourRatingRepository.findByTourId(verifyTour(tourId).getId());
    OptionalDouble average = ratings.stream().mapToInt((rating) -> rating.getScore()).average();
    return average.isPresent()? average.getAsDouble() : null;
  }

  private Tour verifyTour(int tourId) throws NoSuchElementException{
    return tourRepository.findById(tourId)
        .orElseThrow(() -> new NoSuchElementException("Tour does not exist: " + tourId));
  }

  private TourRating verifyTourRating(int tourId, Integer customerId) throws NoSuchElementException{
    return tourRatingRepository.findByTourIdAndCustomerId(tourId,customerId)
    .orElseThrow(() -> new NoSuchElementException(String.format("Tour rating does not exist for customer: %d and tour:%d", customerId,tourId)));
  }

}
