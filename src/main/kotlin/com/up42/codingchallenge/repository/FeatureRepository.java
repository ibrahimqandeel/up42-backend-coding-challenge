package com.up42.codingchallenge.repository;

import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface FeatureRepository {
    Optional<Map<UUID, FeatureCollection.Feature>> getFeatures();
//    List<Feature> getFeaturesList();

}