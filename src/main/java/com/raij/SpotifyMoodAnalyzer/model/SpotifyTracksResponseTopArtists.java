package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SpotifyTracksResponseTopArtists {
    @JsonProperty("items")
    List<SpotifyTrackTopArtists> items;
}
