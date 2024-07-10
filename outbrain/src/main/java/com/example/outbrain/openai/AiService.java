package com.example.outbrain.openai;

import com.example.outbrain.openai.client.dto.DocumentSummary;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.stereotype.Service;

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
}
