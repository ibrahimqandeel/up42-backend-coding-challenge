package com.up42.codingchallenge.mapper;

import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.dto.FeatureDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FeatureCollectionMapper {

    FeatureDTO mapToFeatureDTO(FeatureCollection.Feature feature);
    List<FeatureDTO> mapToListOfFeatureDTO(List<FeatureCollection.Feature> features);

    List<FeatureDTO> mapFromMapToListOfFeatureDTO(Map<UUID, FeatureCollection.Feature> features);
}
