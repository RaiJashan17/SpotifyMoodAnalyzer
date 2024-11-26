package com.raij.SpotifyMoodAnalyzer.database;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends CassandraRepository<User, String> {
    List<User> findByUserId(String userId);
    List<User> findByUserIdAndCalendarDateBetween(String userId, Date startDate, Date endDate);
}
