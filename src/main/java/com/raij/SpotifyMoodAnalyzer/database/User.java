package com.raij.SpotifyMoodAnalyzer.database;

import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrackTopArtists;
import com.raij.SpotifyMoodAnalyzer.model.SpotifyTrackTopSongs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Table(value = "dev") @Getter @Setter @ToString
public class User {
    @PrimaryKeyColumn(name="user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    String userId;

    @PrimaryKeyColumn(name="calendar_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    Date calendarDate;

    @CassandraType(type=CassandraType.Name.TEXT)
    private String period;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> topSongs;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> topArtists;
}
