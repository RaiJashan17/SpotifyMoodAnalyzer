package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Album {
    @JsonProperty("name")
    private String name; // Album name

    // Getters
    public String getName() {
        return name;
    }
}

