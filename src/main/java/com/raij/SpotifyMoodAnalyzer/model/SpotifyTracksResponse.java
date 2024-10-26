package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SpotifyTracksResponse {
    @JsonProperty("items")
    private List<SpotifyTrackWrapper> items;

    // Getters and Setters
    public List<SpotifyTrackWrapper> getItems() {
        return items;
    }

    public void setItems(List<SpotifyTrackWrapper> items) {
        this.items = items;
    }
}

