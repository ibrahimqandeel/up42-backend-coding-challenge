package com.up42.codingchallenge.mapper;

import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.dto.FeatureDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FeatureCollectionMapperImpl implements FeatureCollectionMapper {
    @Override
    public FeatureDTO mapToFeatureDTO(FeatureCollection.Feature feature) {
        if (feature != null) {
            return new FeatureDTO(feature.getId(),
                    feature.getTimestamp(),
                    feature.getBeginViewingDate(),
                    feature.getEndViewingDate(),
                    feature.getMissionName(),
                    feature.getQuicklook());
        }
        return null;
    }

    @Override
    public List<FeatureDTO> mapToListOfFeatureDTO(List<FeatureCollection.Feature> features) {
        if (features != null) {
            return features.stream()
                    .map(feature -> new FeatureDTO(feature.getId(),
                            feature.getTimestamp(),
                            feature.getBeginViewingDate(),
                            feature.getEndViewingDate(),
                            feature.getMissionName(),
                            feature.getQuicklook()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<FeatureDTO> mapFromMapToListOfFeatureDTO(Map<UUID, FeatureCollection.Feature> features) {
        if(features != null){
            return features.values().stream()
                    .map(feature -> new FeatureDTO(feature.getId(),
                            feature.getTimestamp(),
                            feature.getBeginViewingDate(),
                            feature.getEndViewingDate(),
                            feature.getMissionName(),
                            feature.getQuicklook()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
