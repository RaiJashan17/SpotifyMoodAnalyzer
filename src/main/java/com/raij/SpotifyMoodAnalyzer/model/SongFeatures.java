package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SongFeatures {

    @JsonProperty("acousticness")
    private float acousticness;

    @JsonProperty("danceability")
    private float danceability;

    @JsonProperty("energy")
    private float energy;

    @JsonProperty("instrumentalness")
    private float instrumentalness;

    @JsonProperty("liveness")
    private float liveness;

    @JsonProperty("loudness")
    private float loudness;

    @JsonProperty("tempo")
    private float tempo;

    @JsonProperty("valence")
    private float valence;
}
