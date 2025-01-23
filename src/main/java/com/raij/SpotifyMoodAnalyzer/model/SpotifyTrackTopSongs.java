package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @AllArgsConstructor
public class SpotifyTrackTopSongs {
    @JsonProperty("name")
    private String name; // Track name

    @JsonProperty("id")
    private String id; // Track ID

    @JsonProperty("artists")
    private List<Artist> artists; // List of artists

    @JsonProperty("album")
    private Album album; // Album details

}
