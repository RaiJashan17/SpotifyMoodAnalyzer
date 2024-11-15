package com.raij.SpotifyMoodAnalyzer.controller;

import com.raij.SpotifyMoodAnalyzer.model.SongFeatures;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTokenResponse;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrackTopSongs;
import com.raij.SpotifyMoodAnalyzer.service.GeminiService;
import com.raij.SpotifyMoodAnalyzer.service.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
public class SpotifyController {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyController.class);

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private GeminiService geminiService;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    private String accessToken;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public RedirectView login() {
        if (accessToken==null) {
            String authorizationUrl = "https://accounts.spotify.com/authorize" +
                    "?client_id=" + clientId +
                    "&response_type=code" +
                    "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                    "&scope=user-read-recently-played%20user-top-read"; // Add any necessary scopes
            logger.info("Authorization URL: " + authorizationUrl);
            return new RedirectView(authorizationUrl);
        }else{
            return new RedirectView("/done");
        }
    }

    @GetMapping("/callback")
    public RedirectView handleSuccess(@RequestParam("code") String code) {
        logger.info("Reached callback method with code: " + code);
        String tokenUrl = "https://accounts.spotify.com/api/token"; // Spotify token endpoint

        // Set headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare the request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri); // Ensure this matches the registered redirect URI
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        // Send the token request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<SpotifyTokenResponse> responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, SpotifyTokenResponse.class);

        // Handle the response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Successfully received access token
            SpotifyTokenResponse tokenResponse = responseEntity.getBody();
            accessToken = tokenResponse.getAccessToken();
            logger.info("Access Token: " + accessToken);
            // You can store the access token for future API requests
            return new RedirectView("/home"); // Redirect to your desired page after success
        } else {
            // Handle error case
            logger.info("Error: " + responseEntity.getBody());
            return new RedirectView("/error"); // Redirect to an error page
        }
    }

    @GetMapping("/home")
    public RedirectView home() throws IOException {
        // Fetch the last played track using the access token
        //spotifyService.fetchRecentlyPlayedTracks(accessToken);
//        List<SpotifyTrackTopSongs> topTrackLongTerm = spotifyService.getUserLongTerm50TopSongs(accessToken);
//        List<SongFeatures> songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTrackLongTerm);
//        SongFeatures songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
//        geminiService.runAIService(songFeatures, topTrackLongTerm);
        //SpotifyTrack lastPlayedTrack = spotifyService.getLastPlayedTrack(accessToken);
        //List<SpotifyTrack> userTopTracksShortTerm = spotifyService.getLast50PlayedSongs(accessToken);
        return new RedirectView("/done");
    }

    @GetMapping("/longterm")
    public RedirectView longTermAnalysis() throws IOException{
        List<SpotifyTrackTopSongs> topTrackLongTerm = spotifyService.getUserLongTerm50TopSongs(accessToken);
        List<SongFeatures> songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTrackLongTerm);
        SongFeatures songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
        String text1 = "Over the past year, user's top songs are " + topTrackLongTerm.get(0).getName() + " by "+ topTrackLongTerm.get(0).getArtists().get(0) + ", " + topTrackLongTerm.get(1).getName() + " by "+ topTrackLongTerm.get(1).getArtists().get(0) + ", " + topTrackLongTerm.get(2).getName() + " by "+ topTrackLongTerm.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() + ", danceability, energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
        geminiService.runAIService(text1);
        return new RedirectView("/done");
    }

    @GetMapping("/mediumterm")
    public RedirectView mediumTermAnalysis() throws IOException{
        List<SpotifyTrackTopSongs> topTrackLongTerm = spotifyService.getUserMediumTerm50TopSongs(accessToken);
        List<SongFeatures> songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTrackLongTerm);
        SongFeatures songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
        String text1 = "Over the past 6 months, user's top songs are " + topTrackLongTerm.get(0).getName() + " by "+ topTrackLongTerm.get(0).getArtists().get(0) + ", " + topTrackLongTerm.get(1).getName() + " by "+ topTrackLongTerm.get(1).getArtists().get(0) + ", " + topTrackLongTerm.get(2).getName() + " by "+ topTrackLongTerm.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() + ", danceability, energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
        geminiService.runAIService(text1);
        return new RedirectView("/done");
    }

    @GetMapping("/shorterm")
    public RedirectView shortTermAnalysis() throws IOException{
        List<SpotifyTrackTopSongs> topTrackLongTerm = spotifyService.getUserShortTerm50TopSongs(accessToken);
        List<SongFeatures> songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTrackLongTerm);
        SongFeatures songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
        String text1 = "Over the past month, user's top songs are " + topTrackLongTerm.get(0).getName() + " by "+ topTrackLongTerm.get(0).getArtists().get(0) + ", " + topTrackLongTerm.get(1).getName() + " by "+ topTrackLongTerm.get(1).getArtists().get(0) + ", " + topTrackLongTerm.get(2).getName() + " by "+ topTrackLongTerm.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() + ", danceability, energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
        geminiService.runAIService(text1);
        return new RedirectView("/done");
    }

}