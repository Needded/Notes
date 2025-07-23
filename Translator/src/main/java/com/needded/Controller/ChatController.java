package com.needded.Controller;
import com.needded.Model.TranslationResponse;
import com.needded.Service.APIConsumption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/translator")
public class ChatController {

    private final APIConsumption apiConsumption;

    public ChatController(APIConsumption apiConsumption) {
        this.apiConsumption = apiConsumption;
    }

    @GetMapping("view")
    public String translatorPage(){
        return "chat";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String input, Model model) {

        String textTranslated = apiConsumption.translateText(input);

        model.addAttribute("input", input);
        model.addAttribute("result", textTranslated);

        return "chat";
    }
}
