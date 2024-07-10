package com.example.outbrain.openai;

import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.ResourceData;
import jakarta.annotation.Resource;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AiService {
    private final AzureOpenAiChatModel chatModel;

    public AiService(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public DocumentSummary genSummary(String text) {
        if(text.length() > 5) {
            return new DocumentSummary(chatModel.call("Make a summary of this text:" + text));
        }else {
            return new DocumentSummary("The pdf does not contain enough text to make a detailed summary.");
        }
    }

    public ResourceData getKeywords(String text) {
        if(text.length() > 5) {
            String response = chatModel.call("Retreive up to 10 keywords " +
                    "from the following text. Separate the keywords by commas. Text: " + text);
            List<String> keywords = List.of(response.split(", "));
            return new ResourceData(keywords);
        }else {
            return new ResourceData(Collections.singletonList("The pdf does not contain enough text to find keywords."));
        }
    }

}
