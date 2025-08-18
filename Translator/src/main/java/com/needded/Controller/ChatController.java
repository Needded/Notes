package com.needded.Controller;

import com.needded.Model.AskDTO;
import com.needded.Service.APIConsumption;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translator")
public class ChatController {

    private final APIConsumption apiConsumption;

    public ChatController(APIConsumption apiConsumption) {
        this.apiConsumption = apiConsumption;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody AskDTO askDTO) {

        String textTranslated = apiConsumption.translateText(askDTO.toString());

        return ResponseEntity.ok(textTranslated);
    }
}
