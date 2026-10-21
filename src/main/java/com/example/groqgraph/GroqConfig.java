package com.example.groqgraph;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GroqConfig {

    @Bean
    ChatModel groqModel(GroqProperties p) {
        return OpenAiChatModel.builder()
                .baseUrl(p.baseUrl())
                .apiKey(p.apiKey())
                .modelName(p.model())
                .temperature(0.3)
                .timeout(Duration.ofSeconds(60))
                .build();
    }
}
