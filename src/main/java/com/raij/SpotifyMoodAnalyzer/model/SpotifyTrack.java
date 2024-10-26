package com.raij.SpotifyMoodAnalyzer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.List;

public class SpotifyTrack {
    @JsonProperty("name")
    private String name; // Track name

    @JsonProperty("id")
    private String id; // Track ID

    @JsonProperty("artists")
    private List<Artist> artists; // List of artists

    @JsonProperty("album")
    private Album album; // Album details

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Album getAlbum() {
        return album;
    }

    public String toString(){
        return "Song Name: " + getName() + ", Album Name: " + getAlbum().getName() + ", Artist: " + getArtists().get(0).getName() + ", Song Id: " + getId();
    }
}


