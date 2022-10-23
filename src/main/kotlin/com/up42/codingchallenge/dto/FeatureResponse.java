package com.up42.codingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FeatureResponse {
    @JsonProperty
    private List<FeatureDTO> features;

    public FeatureResponse(){}

    public FeatureResponse(List<FeatureDTO> features){
        this.features = new ArrayList<>(features);
    }

    public List<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDTO> features) {
        this.features = features;
    }
}
