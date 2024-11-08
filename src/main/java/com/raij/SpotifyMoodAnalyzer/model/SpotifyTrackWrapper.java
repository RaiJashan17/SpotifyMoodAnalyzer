package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SpotifyTrackWrapper {

    @JsonProperty("track")
    private SpotifyTrack track;

}
