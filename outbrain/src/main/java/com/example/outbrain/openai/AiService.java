package com.example.outbrain.openai;

import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.openai.client.dto.ResponseMode;
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

    public DocumentSummary genSummary(String text, ResponseMode mode, int wordLimit) {
        System.out.println(mode.toString());
        String modeDescription =
                (mode == ResponseMode.detailed) ? (modeDescription = "Try to be detailed as possible. " +
                        "Make sure you include every topic from the text.")
                : (modeDescription = "Try to be as concise and short as possible, preserving key topics.");
        if(text.length() > 5) {
            String response = chatModel.call("Make a summary of the following text." + modeDescription
                    + "Do not add special characters (eg. line breaks). Keep it below "+wordLimit+ "word limit. Text: "+ text);
            return new DocumentSummary(response);
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
