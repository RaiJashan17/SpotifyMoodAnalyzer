package com.raij.SpotifyMoodAnalyzer;

import com.raij.SpotifyMoodAnalyzer.database.DataStaxAstraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;

@SpringBootApplication @EnableAutoConfiguration
public class SpotifyMoodAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyMoodAnalyzerApplication.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}
}
