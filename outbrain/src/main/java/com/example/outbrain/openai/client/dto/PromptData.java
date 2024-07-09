package com.example.outbrain.openai.client.dto;

public record PromptData(
        String prompt,
        String document
) {
}
