package com.social.openapi.controller;

import com.social.openapi.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat-bean")
    public CountryCities chatBean(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message).
                call().entity(CountryCities.class);
    }

    @GetMapping("/chat-list")
    public List<String> chatList(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message).
                call().entity(new ListOutputConverter());
    }

    @GetMapping("/chat-map")
    public Map<String,Object> chatMap(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message).
                call().entity(new MapOutputConverter());
    }

    @GetMapping("/chat-bean-list")
    public List<CountryCities> chatBeanList(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message).
                //returns list of class as POJO
                call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
    }
}
