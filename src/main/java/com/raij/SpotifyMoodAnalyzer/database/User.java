package com.raij.SpotifyMoodAnalyzer.database;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Table(value = "music_by_user") @Getter @Setter @ToString
public class User {
    @PrimaryKeyColumn(name="user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    String userId;

    @PrimaryKeyColumn(name="calendar_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    Date calendarDate;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float acousticness;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float danceability;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float energy;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float instrumentalness;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float liveness;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float loudness;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float tempo;

    @CassandraType(type=CassandraType.Name.FLOAT)
    private float valence;

    @CassandraType(type=CassandraType.Name.TEXT)
    private String period;
}
