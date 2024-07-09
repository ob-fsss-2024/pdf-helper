package com.example.outbrain.openai;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;

import java.io.File;
import java.io.IOException;

import static org.apache.pdfbox.Loader.loadPDF;

public class AiService {
    private final AzureOpenAiChatModel chatModel;

    public AiService(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String genSummary(String text) {
        if(text.length() > 5) {
            return chatModel.call("Make a summary of this text:" + text);
        }else {
            return "The pdf does not contain enough text to make a detailed summary.";
        }
    }
}
