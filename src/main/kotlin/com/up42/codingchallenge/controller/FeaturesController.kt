package com.up42.codingchallenge.controller

import com.up42.codingchallenge.dto.FeatureResponse
import com.up42.codingchallenge.service.FeatureService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/features")
class FeaturesController(private val featureService: FeatureService) {
    @GetMapping
    fun getFeatures(): ResponseEntity<FeatureResponse>? {
        return ResponseEntity.ok()
            .body(FeatureResponse(featureService.features))
    }

    @GetMapping("{featureId}/quicklook", produces = [MediaType.IMAGE_PNG_VALUE])
    fun getQuicklookField(@PathVariable featureId: UUID): ResponseEntity<ByteArray>? {
        val decodedBytes = Base64.getDecoder().decode(featureService.getQuicklookFieldByFeatureId(featureId))
        return ResponseEntity.ok()
            .body(decodedBytes)
    }
}

