package com.up42.codingchallenge.service;

import com.up42.codingchallenge.dto.FeatureDTO;
import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.exception.NoContentFoundException;
import com.up42.codingchallenge.mapper.FeatureCollectionMapper;
import com.up42.codingchallenge.repository.FeatureRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeatureServiceImpl implements FeatureService {
    private final FeatureRepository featureRepositoryFile;
    private final CacheManager cacheManager;

    private final FeatureCollectionMapper featureCollectionMapper;

    public FeatureServiceImpl(FeatureRepository featureRepository,
                              CacheManager cacheManager,
                              FeatureCollectionMapper featureCollectionMapper) {
        this.featureRepositoryFile = featureRepository;
        this.cacheManager = cacheManager;
        this.featureCollectionMapper = featureCollectionMapper;
    }

    @Override
    public List<FeatureDTO> getFeatures() {
        //load features from the cache
        Optional<Map<UUID, FeatureCollection.Feature>> features = getCachedFeatures();

        //load features from the repository
        if (features.isEmpty()) {
            features = featureRepositoryFile.getFeatures();
        }

        //map to list of FeatureDTO if available
        if(features.isPresent() && !features.get().isEmpty()){
            return featureCollectionMapper
                    .mapFromMapToListOfFeatureDTO(features.get());
        }

        return Collections.emptyList();
    }

    public String getQuicklookFieldByFeatureId(UUID featureId) {
        //load features from the cache
        Optional<FeatureCollection.Feature> feature = Optional.ofNullable(
                getCachedFeatures()
                        .orElse(Collections.emptyMap())
                        .get(featureId)
        );

        if (feature.isPresent()) {
            return feature.get().getQuicklook();
        }

        //load features from the repository
        return Optional.ofNullable(
                        featureRepositoryFile.getFeatures()
                                .orElse(Collections.emptyMap())
                                .get(featureId))
                .orElseThrow(() -> new NoContentFoundException("No image found for this feature: " + featureId))
                .getQuicklook();
    }

    private Optional<Map<UUID, FeatureCollection.Feature>> getCachedFeatures() {
        Cache featuresCache = cacheManager.getCache("features");
        if(featuresCache != null){
            return Optional.ofNullable(featuresCache.get(SimpleKey.EMPTY, Map.class));
        }
        return Optional.empty();
    }
}
