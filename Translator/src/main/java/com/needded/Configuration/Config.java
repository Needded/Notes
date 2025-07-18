package com.needded.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    private static final String TOKEN;

    static {
        Dotenv dotenv = Dotenv.load();
        TOKEN = dotenv.get("HUGGING_FACE_API_TOKEN");
    }

    @Bean
    public WebClient huggingFaceWebClient() {
        return WebClient.builder()
                .baseUrl("https://api-inference.huggingface.co")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
