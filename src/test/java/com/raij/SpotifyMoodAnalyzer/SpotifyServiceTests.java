package com.raij.SpotifyMoodAnalyzer;

import com.raij.SpotifyMoodAnalyzer.database.User;
import com.raij.SpotifyMoodAnalyzer.database.UserRepository;
import com.raij.SpotifyMoodAnalyzer.model.*;
import com.raij.SpotifyMoodAnalyzer.service.SpotifyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpotifyServiceTests {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private UserRepository userRepository;

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

    @Test
    void testGetUserMediumTerm5TopSongs_SuccessfulResponse() {
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
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserMediumTerm5TopSongs(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Track1", result.get(0).getName());
        assertEquals("Id1", result.get(0).getId());
    }

    @Test
    void testGetUserMediumTerm5TopSongs_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserMediumTerm5TopSongs(accessToken);

        assertNull(result);
    }

    @Test
    void testGetUserLongTerm5TopSongs_SuccessfulResponse() {
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
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserLongTerm5TopSongs(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Track1", result.get(0).getName());
        assertEquals("Id1", result.get(0).getId());
    }

    @Test
    void testGetUserLongTerm5TopSongs_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopSongs> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopSongs.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopSongs> result = spotifyService.getUserLongTerm5TopSongs(accessToken);

        assertNull(result);
    }

    @Test
    void testGetUserShortTerm5TopArtists_SuccessfulResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        SpotifyTrackTopArtists artist1 = new SpotifyTrackTopArtists("Id1", "Artist1");
        SpotifyTrackTopArtists artist2 = new SpotifyTrackTopArtists("Id2", "Artist2");
        SpotifyTrackTopArtists artist3 = new SpotifyTrackTopArtists("Id3", "Artist3");
        SpotifyTrackTopArtists artist4 = new SpotifyTrackTopArtists("Id4", "Artist4");
        SpotifyTrackTopArtists artist5 = new SpotifyTrackTopArtists("Id5", "Artist5");
        List<SpotifyTrackTopArtists> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);
        mockArtists.add(artist3);
        mockArtists.add(artist4);
        mockArtists.add(artist5);

        SpotifyTracksResponseTopArtists mockResponse = new SpotifyTracksResponseTopArtists();
        mockResponse.setItems(mockArtists);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserShortTerm5TopArtists(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Id1", result.get(0).getId());
        assertEquals("Artist1", result.get(0).getName());
    }

    @Test
    void testGetUserShortTerm5TopArtists_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserShortTerm5TopArtists(accessToken);

        assertNull(result);
    }

    @Test
    void testGetUserMediumTerm5TopArtists_SuccessfulResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        SpotifyTrackTopArtists artist1 = new SpotifyTrackTopArtists("Id1", "Artist1");
        SpotifyTrackTopArtists artist2 = new SpotifyTrackTopArtists("Id2", "Artist2");
        SpotifyTrackTopArtists artist3 = new SpotifyTrackTopArtists("Id3", "Artist3");
        SpotifyTrackTopArtists artist4 = new SpotifyTrackTopArtists("Id4", "Artist4");
        SpotifyTrackTopArtists artist5 = new SpotifyTrackTopArtists("Id5", "Artist5");
        List<SpotifyTrackTopArtists> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);
        mockArtists.add(artist3);
        mockArtists.add(artist4);
        mockArtists.add(artist5);

        SpotifyTracksResponseTopArtists mockResponse = new SpotifyTracksResponseTopArtists();
        mockResponse.setItems(mockArtists);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=medium_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserMediumTerm5TopArtists(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Id1", result.get(0).getId());
        assertEquals("Artist1", result.get(0).getName());
    }

    @Test
    void testGetUserMediumTerm5TopArtists_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=medium_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserMediumTerm5TopArtists(accessToken);

        assertNull(result);
    }

    @Test
    void testGetUserLongTerm5TopArtists_SuccessfulResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        SpotifyTrackTopArtists artist1 = new SpotifyTrackTopArtists("Id1", "Artist1");
        SpotifyTrackTopArtists artist2 = new SpotifyTrackTopArtists("Id2", "Artist2");
        SpotifyTrackTopArtists artist3 = new SpotifyTrackTopArtists("Id3", "Artist3");
        SpotifyTrackTopArtists artist4 = new SpotifyTrackTopArtists("Id4", "Artist4");
        SpotifyTrackTopArtists artist5 = new SpotifyTrackTopArtists("Id5", "Artist5");
        List<SpotifyTrackTopArtists> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);
        mockArtists.add(artist3);
        mockArtists.add(artist4);
        mockArtists.add(artist5);

        SpotifyTracksResponseTopArtists mockResponse = new SpotifyTracksResponseTopArtists();
        mockResponse.setItems(mockArtists);

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserLongTerm5TopArtists(accessToken);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals("Id1", result.get(0).getId());
        assertEquals("Artist1", result.get(0).getName());
    }

    @Test
    void testGetUserLongTerm5TopArtists_NullResponse() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<SpotifyTracksResponseTopArtists> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=5&offset=0"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SpotifyTracksResponseTopArtists.class)
        )).thenReturn(responseEntity);

        List<SpotifyTrackTopArtists> result = spotifyService.getUserLongTerm5TopArtists(accessToken);

        assertNull(result);
    }

    @Test
    void testGetUser_SucessfulResponse(){
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";
        UserInfo mockUser = new UserInfo("displayName", "userId");

        ResponseEntity<UserInfo> responseEntity =
                new ResponseEntity<>(mockUser, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserInfo.class)
        )).thenReturn(responseEntity);

        UserInfo result = spotifyService.getUser(accessToken);

        assertNotNull(result);
        assertEquals("displayName", result.getDisplayName());
        assertEquals("userId", result.getUserId());
    }

    @Test
    void testGetUser_NullResponse(){
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";

        ResponseEntity<UserInfo> responseEntity =
                new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                eq("https://api.spotify.com/v1/me/"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserInfo.class)
        )).thenReturn(responseEntity);

        UserInfo result = spotifyService.getUser(accessToken);

        assertNull(result);
    }

    @Test
    void testCreateUserData() {
        String accessToken = "2TpxZ7JUBn3uw46aR7qd6V";
        UserInfo mockUser = new UserInfo("displayName", "userId");
        Date date = new Date();
        String period = "shortterm";

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

        SpotifyTrackTopArtists artist1 = new SpotifyTrackTopArtists("Id1", "Artist1");
        SpotifyTrackTopArtists artist2 = new SpotifyTrackTopArtists("Id2", "Artist2");
        SpotifyTrackTopArtists artist3 = new SpotifyTrackTopArtists("Id3", "Artist3");
        SpotifyTrackTopArtists artist4 = new SpotifyTrackTopArtists("Id4", "Artist4");
        SpotifyTrackTopArtists artist5 = new SpotifyTrackTopArtists("Id5", "Artist5");
        List<SpotifyTrackTopArtists> mockArtists = new ArrayList<>();
        mockArtists.add(artist1);
        mockArtists.add(artist2);
        mockArtists.add(artist3);
        mockArtists.add(artist4);
        mockArtists.add(artist5);

        User mockUserData = spotifyService.createUserData(mockUser, date, period, mockTracks, mockArtists);
        assertEquals("userId", mockUserData.getUserId());
        assertEquals(date, mockUserData.getCalendarDate());
        assertEquals(period, mockUserData.getPeriod());
    }
}

