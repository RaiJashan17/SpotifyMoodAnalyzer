package com.raij.SpotifyMoodAnalyzer;

import com.raij.SpotifyMoodAnalyzer.model.Album;
import com.raij.SpotifyMoodAnalyzer.model.Artist;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrackTopSongs;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTracksResponseTopSongs;
import com.raij.SpotifyMoodAnalyzer.service.SpotifyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpotifyServiceTests {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserShortTerm5TopSongs_SuccessfulResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        SpotifyTrackTopSongs track1 = new SpotifyTrackTopSongs("Track1", "Id1", new ArrayList<>(Arrays.asList(new Artist("artist1"))), new Album("album1"));
        SpotifyTrackTopSongs track2 = new SpotifyTrackTopSongs("Track2", "Id2", new ArrayList<>(Arrays.asList(new Artist("artist2"))), new Album("album2"));
        SpotifyTrackTopSongs track3 = new SpotifyTrackTopSongs("Track3", "Id3", new ArrayList<>(Arrays.asList(new Artist("artist3"))), new Album("album3"));
        SpotifyTrackTopSongs track4 = new SpotifyTrackTopSongs("Track4", "Id4", new ArrayList<>(Arrays.asList(new Artist("artist4"))), new Album("album4"));
        SpotifyTrackTopSongs track5 = new SpotifyTrackTopSongs("Track5", "Id5", new ArrayList<>(Arrays.asList(new Artist("artist5"))), new Album("album5"));
        List<SpotifyTrackTopSongs> mockTracks = new ArrayList<>();
        mockTracks.add(track1);
        mockTracks.add(track2);
        mockTracks.add(track3);
        mockTracks.add(track4);
        mockTracks.add(track5);

        SpotifyTracksResponseTopSongs mockResponse = new SpotifyTracksResponseTopSongs();
        mockResponse.setItems(mockTracks);

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserShortTerm5TopSongs(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Track1", result.get(0).getName());
        assertEquals("Id1", result.get(0).getId());
    }

    @Test
    void testGetUserShortTerm5TopSongs_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserShortTerm5TopSongs(accessToken);

        assertNull(result);
    }
}
