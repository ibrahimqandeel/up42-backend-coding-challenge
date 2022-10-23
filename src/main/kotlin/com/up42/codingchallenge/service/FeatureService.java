package com.up42.codingchallenge.service;

import com.up42.codingchallenge.dto.FeatureDTO;

import java.util.List;
import java.util.UUID;

public interface FeatureService {
    List<FeatureDTO> getFeatures();
    String getQuicklookFieldByFeatureId(UUID featureId);
}
