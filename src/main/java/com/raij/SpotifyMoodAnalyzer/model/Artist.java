package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {
    @JsonProperty("name")
    private String name; // Artist name

    // Getters
    public String getName() {
        return name;
    }
}

