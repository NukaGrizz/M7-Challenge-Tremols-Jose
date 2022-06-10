package com.trilogy.musicstorerecommendations.repository;


import com.trilogy.musicstorerecommendations.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Integer> {
}
