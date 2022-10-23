package com.up42.codingchallenge.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.exception.JsonFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

@Repository
public class FeatureRepositoryFileImpl implements FeatureRepository {
    private final String filePath;

    public FeatureRepositoryFileImpl(@Value("${file.path.featureCollection}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    @CachePut(value = "features")
    public Optional<Map<UUID, FeatureCollection.Feature>> getFeatures(){
        List<FeatureCollection> featureCollections = loadFeatureCollectionsFromJsonFile(filePath);

        return Optional.of(featureCollections.stream()
                .flatMap(featureCollection -> featureCollection.getFeatures().stream()
                        .map(feature -> new FeatureCollection.Feature(null,
                                feature.getProperties().getId(),
                                feature.getProperties().getTimestamp(),
                                feature.getProperties().getAcquisition().getBeginViewingDate(),
                                feature.getProperties().getAcquisition().getEndViewingDate(),
                                feature.getProperties().getAcquisition().getMissionName(),
                                feature.getProperties().getQuicklook())))
                .collect(Collectors.toMap(FeatureCollection.Feature::getId, Function.identity())));
    }

    private List<FeatureCollection> loadFeatureCollectionsFromJsonFile(String filePath) {
        try {
            //load the file
            File jsonFile = new ClassPathResource(filePath).getFile();

            //parse the json file
            return jacksonObjectMapper().readValue(jsonFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new JsonFileNotFoundException("Something went wrong while trying to parse the json file.");
        }
    }
}
