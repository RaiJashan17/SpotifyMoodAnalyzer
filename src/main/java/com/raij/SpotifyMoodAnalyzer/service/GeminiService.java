package com.raij.SpotifyMoodAnalyzer.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class GeminiService {

    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

   public String runAIService(String text1) throws IOException {
       String generatedText = "";
       try (VertexAI vertexAi = new VertexAI("spotifymoodanaylzer", "us-west1"); ) {
           GenerationConfig generationConfig =
                   GenerationConfig.newBuilder()
                           .setMaxOutputTokens(8192)
                           .setTemperature(2F)
                           .setTopP(0.95F)
                           .build();
           List<SafetySetting> safetySettings = Arrays.asList(
                   SafetySetting.newBuilder()
                           .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                           .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                           .build(),
                   SafetySetting.newBuilder()
                           .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                           .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                           .build(),
                   SafetySetting.newBuilder()
                           .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                           .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                           .build(),
                   SafetySetting.newBuilder()
                           .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                           .setThreshold(SafetySetting.HarmBlockThreshold.OFF)
                           .build()
           );
           var textsi_1 = "You are a mood analyzer, trying to understand one's emotions. One way you analyze this is through what a user is listening to. You look through what are their top songs and artists over a time period (think therapist format) their mood and why, what they can do to improve their mood and make some music genre recommendations based on what you have.";
           var systemInstruction = ContentMaker.fromMultiModalData(textsi_1);
           GenerativeModel model =
                   new GenerativeModel.Builder()
                           .setModelName("gemini-1.5-flash-002")
                           .setVertexAi(vertexAi)
                           .setGenerationConfig(generationConfig)
                           .setSafetySettings(safetySettings)
                           .setSystemInstruction(systemInstruction)
                           .build();



           var content = ContentMaker.fromMultiModalData(text1);
           ResponseStream<GenerateContentResponse> responseStream = model.generateContentStream(content);

           for (GenerateContentResponse response : responseStream) {
               generatedText += response.getCandidates(0).getContent().getParts(0).getText(); // Add newline for better readability
           }
           //logger.info(generatedText);
       }
       return generatedText;
    }
}
