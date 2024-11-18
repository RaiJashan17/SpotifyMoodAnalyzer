package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class User {
    @JsonProperty("id")
    String id;

    @JsonProperty("display_name")
    String displayName;

}
