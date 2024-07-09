package com.example.outbrain.openai.client;

import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.openai.client.dto.ResourceData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GptApiClient {
    //returns gpt summary for a pdf provided as text
    @GetExchange("summary")
    DocumentSummary getDocumentSummary(@PathVariable String document);



    //user prompt / chat

    //gets keywords from the pdf to search wiki db
    @GetExchange("query")
    ResourceData getResourceData(@PathVariable PromptData prompt);

}