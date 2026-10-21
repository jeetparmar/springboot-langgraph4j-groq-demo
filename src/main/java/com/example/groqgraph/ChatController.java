package com.example.groqgraph;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    public record ChatRequest(String question) {
    }

    public record ChatResponse(String answer, int revisions, List<String> trace) {
    }

    private final ChatGraphService service;

    public ChatController(ChatGraphService service) {
        this.service = service;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest req) {
        ChatState s = service.run(req.question());
        return new ChatResponse(s.answer(), s.revisions(), s.trace());
    }
}
