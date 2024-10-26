package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyTrackWrapper {
    @JsonProperty("track")
    private SpotifyTrack track;

    // Getters and Setters
    public SpotifyTrack getTrack() {
        return track;
    }

    public void setTrack(SpotifyTrack track) {
        this.track = track;
    }
}
