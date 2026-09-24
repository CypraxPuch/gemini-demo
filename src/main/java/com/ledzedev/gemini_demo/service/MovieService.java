package com.ledzedev.gemini_demo.service;

import com.ledzedev.gemini_demo.model.MovieRecommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieService {
    private final ChatClient chatClient;

    public MovieService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public MovieRecommendation getRecommendation(String query){
        log.debug("Recommendation service...");
        return chatClient.prompt()
                .user("Give me a movie recommendation based on this preference:" + query)
                .call()
                .entity(MovieRecommendation.class);
    }
}
