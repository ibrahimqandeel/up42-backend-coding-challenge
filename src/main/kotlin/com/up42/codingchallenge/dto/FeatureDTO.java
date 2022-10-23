package com.up42.codingchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class FeatureDTO {
    @JsonProperty
    private UUID id;
    @JsonProperty
    private Long timestamp;
    @JsonProperty
    private Long beginViewingDate;
    @JsonProperty
    private Long endViewingDate;
    @JsonProperty
    private String missionName;
    @JsonProperty
    private String quicklook;

    public FeatureDTO(){}

    public FeatureDTO(UUID id, Long timestamp, Long beginViewingDate, Long endViewingDate, String missionName, String quicklook) {
        this.id = id;
        this.timestamp = timestamp;
        this.beginViewingDate = beginViewingDate;
        this.endViewingDate = endViewingDate;
        this.missionName = missionName;
        this.quicklook = quicklook;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getBeginViewingDate() {
        return beginViewingDate;
    }

    public void setBeginViewingDate(Long beginViewingDate) {
        this.beginViewingDate = beginViewingDate;
    }

    public Long getEndViewingDate() {
        return endViewingDate;
    }

    public void setEndViewingDate(Long endViewingDate) {
        this.endViewingDate = endViewingDate;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getQuicklook() {
        return quicklook;
    }

    public void setQuicklook(String quicklook) {
        this.quicklook = quicklook;
    }
}
