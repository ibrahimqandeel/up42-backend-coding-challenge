package com.up42.codingchallenge.service;

import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.dto.FeatureDTO;
import com.up42.codingchallenge.exception.NoContentFoundException;
import com.up42.codingchallenge.mapper.FeatureCollectionMapper;
import com.up42.codingchallenge.repository.FeatureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeatureServiceImplTest {

    private FeatureService underTest;

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache featuresCache;

    @Mock
    private FeatureCollectionMapper featureCollectionMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new FeatureServiceImpl(featureRepository, cacheManager, featureCollectionMapper);
    }

    @Test
    void test_getFeatures_from_repository_then_cache(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(sampleFeaturesMap());
        List<FeatureDTO> expectedResult = sampleFeaturesDTOList();

        //when
        when(featureRepository.getFeatures()).thenReturn(features);
        when(cacheManager.getCache("features")).thenReturn(null);
        when(featureCollectionMapper.mapFromMapToListOfFeatureDTO(any())).thenReturn(expectedResult);
        List<FeatureDTO> actualResultFromRepo = underTest.getFeatures(); //should retrieve from the file

        //And
        when(cacheManager.getCache("features")).thenReturn(featuresCache);
        when(featuresCache.get(SimpleKey.EMPTY, Map.class)).thenReturn(features.get());
        List<FeatureDTO> actualResultFromCache = underTest.getFeatures(); //should retrieve from the cache

        //then
        verify(featureRepository,times(1)).getFeatures();
        assertTrue(features.get().size() == actualResultFromRepo.size()
                && features.get().size() == actualResultFromCache.size());

        assertEquals(expectedResult, actualResultFromRepo);
        assertEquals(expectedResult, actualResultFromCache);
    }

    @Test
    void test_getFeatures_from_cache_only(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(sampleFeaturesMap());
        List<FeatureDTO> expectedResult = sampleFeaturesDTOList();

        //when
        when(cacheManager.getCache("features")).thenReturn(featuresCache);
        when(featuresCache.get(SimpleKey.EMPTY, Map.class)).thenReturn(features.get());
        when(featureCollectionMapper.mapFromMapToListOfFeatureDTO(any())).thenReturn(expectedResult);

        List<FeatureDTO> actualResult = underTest.getFeatures(); //should retrieve from the cache

        //then
        verifyNoInteractions(featureRepository);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void test_getFeatures_when_no_features(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(Collections.emptyMap());

        //when
        when(featureRepository.getFeatures()).thenReturn(features);
        when(cacheManager.getCache("features")).thenReturn(null);
        List<FeatureDTO> actualResult = underTest.getFeatures();

        //then
        verify(featureRepository,times(1)).getFeatures();
        verifyNoInteractions(featuresCache);
        verifyNoInteractions(featureCollectionMapper);
        assertTrue(actualResult.isEmpty());
    }

    @Test
    void test_getQuicklookFieldByFeatureId_from_repository_then_cache(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(sampleFeaturesMap());
        UUID featureOneID = UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0");
        UUID featureTwoID = UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655");
        String expectedResultFeature_one = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFSCAIAAAAL";
        String expectedResultFeature_two = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFaCAIAAAD";

        //when
        when(featureRepository.getFeatures()).thenReturn(features);
        when(cacheManager.getCache("features")).thenReturn(null);
        String actualResultFeature_one = underTest.getQuicklookFieldByFeatureId(featureOneID);

        //And
        when(cacheManager.getCache("features")).thenReturn(featuresCache);
        when(featuresCache.get(SimpleKey.EMPTY, Map.class)).thenReturn(features.get());
        String actualResultFeature_two = underTest.getQuicklookFieldByFeatureId(featureTwoID);

        //then
        verify(featureRepository, times(1)).getFeatures();
        assertEquals(expectedResultFeature_one, actualResultFeature_one);
        assertEquals(expectedResultFeature_two, actualResultFeature_two);
    }

    @Test
    void test_getQuicklookFieldByFeatureId_from_cache_only(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(sampleFeaturesMap());
        UUID featureId = UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0");
        String expectedResult = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFSCAIAAAAL";

        //when
        when(featureRepository.getFeatures()).thenReturn(features);
        when(cacheManager.getCache("features")).thenReturn(featuresCache);
        when(featuresCache.get(SimpleKey.EMPTY, Map.class)).thenReturn(features.get());
        String actualResult = underTest.getQuicklookFieldByFeatureId(featureId);

        //then
        verify(featuresCache, times(1)).get(SimpleKey.EMPTY, Map.class);
        verifyNoInteractions(featureRepository);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void test_getQuicklookFieldByFeatureId_when_no_data(){
        //given
        Optional<Map<UUID, FeatureCollection.Feature>> features =
                Optional.of(sampleFeaturesMap());
        UUID randomId = UUID.randomUUID();
        String expectedMessage = "No image found for this feature: " + randomId;

        //when
        when(featureRepository.getFeatures()).thenReturn(features);
        NoContentFoundException exception = assertThrows(NoContentFoundException.class, () -> underTest.getQuicklookFieldByFeatureId(randomId));

        //then
        assertEquals(expectedMessage, exception.getReason());
    }

    private Map<UUID, FeatureCollection.Feature> sampleFeaturesMap() {
        return Map.of(UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"), new FeatureCollection.Feature(null,
                        UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"),
                        1556904743783L,
                        1556904743783L,
                        1556904768781L,
                        "Sentinel-1B",
                        "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFSCAIAAAAL"),
                UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655"), new FeatureCollection.Feature(null,
                        UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655"),
                        1558155123786L,
                        1558155123786L,
                        1558155148785L,
                        "Sentinel-1A",
                        "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFaCAIAAAD"),
                UUID.fromString("b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5"), new FeatureCollection.Feature(null,
                        UUID.fromString("b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5"),
                        1558155148786L,
                        1558155148786L,
                        1558155173785L,
                        "Sentinel-1A",
                        null)
        );
    }

    private List<FeatureDTO> sampleFeaturesDTOList() {
        return List.of(new FeatureDTO(
                        UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"),
                        1556904743783L,
                        1556904743783L,
                        1556904768781L,
                        "Sentinel-1B",
                        "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFSCAIAAAAL"),
                new FeatureDTO(
                        UUID.fromString("ca81d759-0b8c-4b3f-a00a-0908a3ddd655"),
                        1558155123786L,
                        1558155123786L,
                        1558155148785L,
                        "Sentinel-1A",
                        "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFaCAIAAAD"),
                new FeatureDTO(
                        UUID.fromString("b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5"),
                        1558155148786L,
                        1558155148786L,
                        1558155173785L,
                        "Sentinel-1A",
                        null)
        );
    }
}
