package com.raij.SpotifyMoodAnalyzer.service;

import com.raij.SpotifyMoodAnalyzer.model.*;
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
                    logger.info(trackWrapper.get(i).getTrack().toString());
                    tracks.add(trackWrapper.get(i).getTrack());
                }
                return tracks;
            }


        }
        return null;
    }


    private void saveJsonToFileForRecentlyPlayed(String json) {
        try (FileWriter fileWriter = new FileWriter("recently_played_tracks.json")) {
            fileWriter.write(json);
            logger.info("JSON saved to recently_played_tracks.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveJsonToFileForTopShortTerm(String json) {
        try (FileWriter fileWriter = new FileWriter("top_track_short_term.json")) {
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
            saveJsonToFileForRecentlyPlayed(jsonResponse);

            // You can also parse the JSON here if needed
        } else {
            logger.info("Failed to fetch data: " + response.getStatusCode());
        }
    }

    public SpotifyTrackTopSongs getUserShortTermTopSong(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=1&offset=0"; // Example API URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopSongs.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopSongs tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                SpotifyTrackTopSongs track = tracksResponse.getItems().get(0);

                // Extract required fields
                logger.info(track.toString());
                // Log extracted fields
                return track; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrackTopSongs> getUserShortTerm50TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=50&offset=0"; // Example API URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopSongs.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopSongs tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopSongs> tracks = tracksResponse.getItems();
                logger.info(tracks.toString());
                return tracks; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrackTopSongs> getUserMediumTerm50TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=50&offset=0"; // Example API URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopSongs.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopSongs tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopSongs> tracks = tracksResponse.getItems();
                logger.info(tracks.toString());
                return tracks; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrackTopSongs> getUserLongTerm50TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=50&offset=0"; // Example API URL
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopSongs.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopSongs tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopSongs> tracks = tracksResponse.getItems();
                logger.info(tracks.toString());
                return tracks; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public SongFeatures getSongFeatures(String accessToken, SpotifyTrack spotifyTrack){

        String apiUrl = "https://api.spotify.com/v1/audio-features/" + spotifyTrack.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SongFeatures> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SongFeatures.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());
        logger.info(responseEntity.getBody().toString());
    return responseEntity.getBody();
    }
}
