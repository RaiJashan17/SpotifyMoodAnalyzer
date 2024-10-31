package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpotifyTracksResponseTopSongs {
    @JsonProperty("items")
    private List<SpotifyTrackTopSongs> items;

    // Getters and Setters
    public List<SpotifyTrackTopSongs> getItems() {
        return items;
    }

    public void setItems(List<SpotifyTrackTopSongs> items) {
        this.items = items;
    }
}
