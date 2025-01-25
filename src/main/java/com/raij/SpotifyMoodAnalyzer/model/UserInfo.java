package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class UserInfo {

    @JsonProperty("display_name")
    String displayName;

    @JsonProperty("id")
    String userId;
}
