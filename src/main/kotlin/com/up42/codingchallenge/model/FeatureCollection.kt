package com.up42.codingchallenge.com.up42.codingchallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeatureCollection(var features: List<Feature>) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feature(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var properties: Properties? = null,
        var id: UUID?,
        var timestamp: Long?,
        var beginViewingDate: Long?,
        var endViewingDate: Long?,
        var missionName: String?,
        var quicklook: String?
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Properties(
            var id: UUID?,
            var timestamp: Long?,
            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
            var acquisition: Acquisition? = null,
            var quicklook: String?
        ) {

            @JsonIgnoreProperties(ignoreUnknown = true)
            data class Acquisition(
                var beginViewingDate: Long?,
                var endViewingDate: Long?,
                var missionName: String?
            )
        }
    }
}