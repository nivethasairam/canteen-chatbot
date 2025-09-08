package com.example.chatbot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final RAGService ragService;

    public ChatController(RAGService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String query) {
        String response = ragService.chat(query);
        return ResponseEntity.ok(response);
    }
}
