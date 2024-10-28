package com.raij.SpotifyMoodAnalyzer.service;

import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrack;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrackWrapper;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTracksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    private RestTemplate restTemplate;

    public SpotifyTrack getLastPlayedTrack(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/recently-played?limit=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponse.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponse tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                SpotifyTrackWrapper trackWrapper = tracksResponse.getItems().get(0);
                SpotifyTrack track = trackWrapper.getTrack();

                // Extract required fields
                String artistName = track.getArtists().get(0).getName();
                String albumName = track.getAlbum().getName();
                String trackId = track.getId();
                String trackTitle = track.getName();
                logger.info(track.toString());
                // Log extracted fields
                return track; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrack> getLast50PlayedSongs(String accessToken) {
        String url = "https://api.spotify.com/v1/me/player/recently-played?limit=50";
        List<SpotifyTrack> tracks = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponse.class
        );
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponse tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackWrapper> trackWrapper = tracksResponse.getItems();
                for (int i = 0; i < trackWrapper.size(); i++) {
                    String aristName = trackWrapper.get(i).getTrack().getArtists().get(0).getName();
                    String albumName = trackWrapper.get(i).getTrack().getAlbum().getName();
                    String trackId = trackWrapper.get(i).getTrack().getId();
                    String trackTitle = trackWrapper.get(i).getTrack().getName();
                    logger.info(trackWrapper.get(i).getTrack().toString());
                    tracks.add(trackWrapper.get(i).getTrack());
                }
                return tracks;
            }


        }
        return null;
    }



    private void saveJsonToFile(String json) {
        try (FileWriter fileWriter = new FileWriter("recently_played_tracks.json")) {
            fileWriter.write(json);
            logger.info("JSON saved to recently_played_tracks.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchRecentlyPlayedTracks(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/player/recently-played?limit=1"; // Example API URL
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String jsonResponse = response.getBody();
            // Save JSON response to file
            saveJsonToFile(jsonResponse);

            // You can also parse the JSON here if needed
        } else {
            logger.info("Failed to fetch data: " + response.getStatusCode());
        }
    }
}
