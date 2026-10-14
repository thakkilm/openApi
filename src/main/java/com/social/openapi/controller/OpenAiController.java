package com.social.openapi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OpenAiController {

    private final ChatClient chatClient;

    public  OpenAiController(ChatClient chatClientBuilder) {
    this.chatClient = chatClientBuilder;
    }

    @GetMapping("/chat")
    public String openAiChat(@RequestParam("message") String message){
        return chatClient.prompt().user(message).
                call().content();
    }
}
