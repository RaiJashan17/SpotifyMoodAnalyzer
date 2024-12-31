package com.raij.SpotifyMoodAnalyzer.service;

import com.raij.SpotifyMoodAnalyzer.database.User;
import com.raij.SpotifyMoodAnalyzer.database.UserRepository;
import com.raij.SpotifyMoodAnalyzer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public List<SpotifyTrackTopSongs> getUserShortTerm5TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=5&offset=0";
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

    public List<SpotifyTrackTopSongs> getUserMediumTerm5TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=5&offset=0";
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

    public List<SpotifyTrackTopSongs> getUserLongTerm5TopSongs(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5&offset=0"; // Example API URL
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

    public List<SpotifyTrackTopArtists> getUserShortTerm5TopArtists(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=5&offset=0";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopArtists.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopArtists tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopArtists> artists = tracksResponse.getItems();
                logger.info(artists.toString());
                return artists; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrackTopArtists> getUserMediumTerm5TopArtists(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=5&offset=0";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopArtists.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopArtists tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopArtists> artists = tracksResponse.getItems();
                logger.info(artists.toString());
                return artists; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public List<SpotifyTrackTopArtists> getUserLongTerm5TopArtists(String accessToken) {
        String apiUrl = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5&offset=0";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                SpotifyTracksResponseTopArtists.class
        );

        // Log the status code and body
        logger.info("Response Status Code: {}", responseEntity.getStatusCode());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            SpotifyTracksResponseTopArtists tracksResponse = responseEntity.getBody();
            if (tracksResponse != null && !tracksResponse.getItems().isEmpty()) {
                List<SpotifyTrackTopArtists> artists = tracksResponse.getItems();
                logger.info(artists.toString());
                return artists; // Return the complete track object if needed
            }
        }

        return null; // Handle cases where no track is found
    }

    public void saveUserData(UserInfo userInfo, Date date, String period){
        User user=new User();
        user.setUserId(userInfo.getUserId());
        user.setCalendarDate(date);
        user.setPeriod(period);
        userRepository.save(user);
    }
}
