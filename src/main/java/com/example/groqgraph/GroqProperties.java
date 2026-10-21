package com.example.groqgraph;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "groq")
public record GroqProperties(String apiKey, String baseUrl, String model, int maxRevisions) {}
