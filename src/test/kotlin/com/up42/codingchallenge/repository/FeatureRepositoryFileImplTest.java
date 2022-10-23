package com.up42.codingchallenge.repository;

import com.up42.codingchallenge.com.up42.codingchallenge.model.FeatureCollection;
import com.up42.codingchallenge.exception.JsonFileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeatureRepositoryFileImplTest {

    private FeatureRepositoryFileImpl underTest;
    @Value("${file.path.featureCollection}")
    private String jsonFilePath;

    @BeforeEach
    void setUp(){
        underTest = new FeatureRepositoryFileImpl(jsonFilePath);
    }

    @Test
    @DisplayName("Test get features when the json file is persist")
    void test_getFeatures_read_from_valid_json_file() {
        //given
        Map<UUID, FeatureCollection.Feature> expectedResult = sampleFeaturesMap();

        //when
        Optional<Map<UUID, FeatureCollection.Feature>> actualResult = underTest.getFeatures();

        //then
        assertEquals(expectedResult.size(), actualResult.get().size());
        assertEquals(actualResult.get(), expectedResult);
    }

    @Test
    @DisplayName("Test get features when the json file is not found")
    void test_getFeatures_when_json_file_is_not_found() {
        //given
        underTest = new FeatureRepositoryFileImpl("");
        String expectedMessage = "Something went wrong while trying to parse the json file.";

        //when
        JsonFileNotFoundException exception = assertThrows(JsonFileNotFoundException.class, () -> underTest.getFeatures());

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
}