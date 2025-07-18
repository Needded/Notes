package com.needded.Translator.ServiceTest;

import com.needded.Service.APIConsumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class APIConsumptionTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private APIConsumption apiConsumption;

//    @Test
//    void whenTranslationReturned_thenCorrectAnswer() {
//
//        when(apiConsumption.translateText("I like Java.")).thenReturn("Eu gosto de Java.");
//
//    }

}
