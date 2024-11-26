package com.raij.SpotifyMoodAnalyzer.service;

import com.raij.SpotifyMoodAnalyzer.database.User;
import com.raij.SpotifyMoodAnalyzer.database.UserRepository;
import com.raij.SpotifyMoodAnalyzer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class SpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final UserRepository userRepository;

    public SpotifyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
                //logger.info(tracks.toString());
                return tracks; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public SongFeatures getOneSongFeatures(String accessToken, SpotifyTrack spotifyTrack) {

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

    public List<SongFeatures> getAllSongFeatures(String accessToken, List<SpotifyTrackTopSongs> spotifyTrackList) {
        List<SongFeatures> songFeaturesList = new ArrayList<>();
        for (SpotifyTrackTopSongs spotifyTrack : spotifyTrackList) {
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
            //logger.info("Response Status Code: {}", responseEntity.getStatusCode());
            //logger.info(responseEntity.getBody().toString());
            songFeaturesList.add(responseEntity.getBody());
        }

        return songFeaturesList;
    }

    public SongFeatures averageOfSongFeatures(List<SongFeatures> songFeaturesList) {
        SongFeatures songFeatures = new SongFeatures();
        Float acousticness = 0F;
        Float danceability = 0F;
        Float energy = 0F;
        Float instrumentalness = 0F;
        Float liveness = 0F;
        Float loudness = 0F;
        Float tempo = 0F;
        Float valence = 0F;
        for (SongFeatures songFeature : songFeaturesList) {
            acousticness = acousticness + songFeature.getAcousticness();
            danceability = danceability + songFeature.getDanceability();
            energy = energy + songFeature.getEnergy();
            instrumentalness = instrumentalness + songFeature.getInstrumentalness();
            liveness = liveness + songFeature.getLiveness();
            loudness = loudness + songFeature.getLoudness();
            tempo = tempo + songFeature.getTempo();
            valence = valence + songFeature.getValence();
        }
        songFeatures.setAcousticness(acousticness / songFeaturesList.size());
        songFeatures.setDanceability(danceability / songFeaturesList.size());
        songFeatures.setEnergy(energy / songFeaturesList.size());
        songFeatures.setInstrumentalness(instrumentalness / songFeaturesList.size());
        songFeatures.setLiveness(liveness / songFeaturesList.size());
        songFeatures.setLoudness(loudness / songFeaturesList.size());
        songFeatures.setTempo(tempo / songFeaturesList.size());
        songFeatures.setValence(valence / songFeaturesList.size());
        logger.info(songFeatures.toString());
        return songFeatures;
    }

    public UserInfo getUser(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserInfo> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                UserInfo.class
        );
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            UserInfo userResponse = responseEntity.getBody();
            logger.info(userResponse.toString());
            return userResponse;
        }
        return null;
    }

    public void saveUserData(UserInfo userInfo, Date date, SongFeatures songFeatures, String period){
        User user=new User();
        user.setUserId(userInfo.getUserId());
        user.setCalendarDate(date);
        user.setAcousticness(songFeatures.getAcousticness());
        user.setDanceability(songFeatures.getDanceability());
        user.setEnergy(songFeatures.getEnergy());
        user.setInstrumentalness(songFeatures.getInstrumentalness());
        user.setLiveness(songFeatures.getLiveness());
        user.setLoudness(songFeatures.getLoudness());
        user.setTempo(songFeatures.getTempo());
        user.setValence(songFeatures.getValence());
        user.setPeriod(period);
        userRepository.save(user);
    }
}
