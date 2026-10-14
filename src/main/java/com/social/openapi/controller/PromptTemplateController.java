package com.social.openapi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    private final ChatClient chatClient;
    @Value("classpath:promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;
//    String promptTemplate= """
//               A customer named {customerName} sent the following message:
//               "{customerMessage}" Write a polite and helpful email response addressing the issue.
//                Maintain a professional tone and provide reassurance.
//                Respond as if you're writing the email body only. Don't include subject, signature
//            """;

    public PromptTemplateController(ChatClient chatClientBuilder) {
    this.chatClient = chatClientBuilder;
    }

    @GetMapping("/email")
    public String emailResponse(@RequestParam("customerName") String customerName,@RequestParam("customerMessage") String customerMessage){
        return chatClient.prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email
                        responses to improve the productivity of the customer support team
                        """).user(promptTemplateSpec-> promptTemplateSpec.text(userPromptTemplate)
                        .param("customerName",customerName)
                        .param("customerMessage",customerMessage)).
                call().content();
    }
}
