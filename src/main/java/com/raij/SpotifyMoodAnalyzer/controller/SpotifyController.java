package com.raij.SpotifyMoodAnalyzer.controller;

import com.raij.SpotifyMoodAnalyzer.model.*;
import com.raij.SpotifyMoodAnalyzer.service.GeminiService;
import com.raij.SpotifyMoodAnalyzer.service.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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

    private String geminiResponse;

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
            return new RedirectView("/home");
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
    public String home() {
        return "home";
    }

    @GetMapping("/analyze")
    public String analyze(@RequestParam("period") String period, Model model) throws IOException {
        String analysisResult = runAnalysis(period);
        logger.info(analysisResult);
        model.addAttribute("geminiResponse", analysisResult);
        return "done"; // Make sure this matches your template file name (done.html)
    }


    private String runAnalysis(String period) throws IOException {
        List<SpotifyTrackTopSongs> topTracks;
        List<SongFeatures> songFeaturesList;
        SongFeatures songFeatures;
        String text;
        UserInfo userInfo;
        Date date;
        switch (period) {
            case "shortterm":
                topTracks = spotifyService.getUserShortTerm50TopSongs(accessToken);
                songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTracks);
                songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
                text = "Over the past month, user's top songs are " + topTracks.get(0).getName() + " by "+ topTracks.get(0).getArtists().get(0) + ", " + topTracks.get(1).getName() + " by "+ topTracks.get(1).getArtists().get(0) + ", " + topTracks.get(2).getName() + " by "+ topTracks.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() + ", danceability " + songFeatures.getDanceability() + " energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
                break;
            case "mediumterm":
                topTracks = spotifyService.getUserMediumTerm50TopSongs(accessToken);
                songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTracks);
                songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
                text = "Over the past six months, user's top songs are " + topTracks.get(0).getName() + " by "+ topTracks.get(0).getArtists().get(0) + ", " + topTracks.get(1).getName() + " by "+ topTracks.get(1).getArtists().get(0) + ", " + topTracks.get(2).getName() + " by "+ topTracks.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() +  ", danceability " + songFeatures.getDanceability() + ", energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
                break;
            case "longterm":
                topTracks = spotifyService.getUserLongTerm50TopSongs(accessToken);
                songFeaturesList = spotifyService.getAllSongFeatures(accessToken, topTracks);
                songFeatures = spotifyService.averageOfSongFeatures(songFeaturesList);
                text = "Over the past year, user's top songs are " + topTracks.get(0).getName() + " by "+ topTracks.get(0).getArtists().get(0) + ", " + topTracks.get(1).getName() + " by "+ topTracks.get(1).getArtists().get(0) + ", " + topTracks.get(2).getName() + " by "+ topTracks.get(2).getArtists().get(0) + ".In addition, the average song acousticness " + songFeatures.getAcousticness() + ", danceability " + songFeatures.getDanceability() +" , energy " + songFeatures.getEnergy() + ", instrumentalness " + songFeatures.getInstrumentalness() + ", liveness " + songFeatures.getLiveness() + ", loudness " + songFeatures.getLoudness() + ",  tempo " + songFeatures.getTempo() +  ", and valence " + songFeatures.getValence() + ". Based on these music stats, how would you think this user is doing in terms of mood?";
                break;
            default:
                return "Error";
        }
        userInfo = spotifyService.getUser(accessToken);
        date = new Date();
        spotifyService.saveUserData(userInfo, date, songFeatures, period);
        // Send the analysis text to Gemini service
        geminiResponse = geminiService.runAIService(text);
        return geminiResponse;
    }
}