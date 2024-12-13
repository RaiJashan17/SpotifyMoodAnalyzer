package com.raij.SpotifyMoodAnalyzer.model;

public class SpotifyTrackEnqueueInput {
    private String trackId;

    public SpotifyTrackEnqueueInput(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
