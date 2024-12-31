package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SpotifyTrackTopArtists {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;
}
