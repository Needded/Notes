package com.needded.Service;

import com.needded.Model.TranslationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class APIConsumption {

    private WebClient webClient;

    //WebClient with HF Api build on "Config" class.
    public APIConsumption(WebClient webClient) {
        this.webClient = webClient;
    }

    public String translateText(String inputText) {


        Map<String, Object> payload = Map.of(
                "inputs", inputText,
                "parameters", Map.of("temperature", 0.0)
        );

        try {
            List<TranslationResponse> response = webClient.post()
                    .uri("/models/unicamp-dl/translation-en-pt-t5")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToFlux(TranslationResponse.class)
                    .collectList()
                    .block();

            if (response != null && !response.isEmpty()) {
                return response.get(0).getTranslation_text();
            } else {
                return "No translation returned.";
            }
        } catch (Exception e) {
            return "Error consulting API: " + e.getMessage();
        }
    }
}
