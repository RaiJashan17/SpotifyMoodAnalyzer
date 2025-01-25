package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor
public class SpotifyTrackTopArtists {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;
}
