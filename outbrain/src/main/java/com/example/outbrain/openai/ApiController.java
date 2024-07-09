package com.example.outbrain.openai;
import com.example.outbrain.openai.client.GptApiClient;
import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.pdf.PDFService;
import com.example.outbrain.wikipedia.WikipediaService;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

public class ApiController {
    private final WikipediaService wikipediaService;
    private final PDFService pdfService;
    private final AiService aiService;


    public ApiController(WikipediaService wikipediaService, PDFService pdfService, AzureOpenAiChatModel chatModel, GptApiClient gptApiClient, AiService aiService){
        this.wikipediaService = wikipediaService;
        this.pdfService = pdfService;
        this.aiService = aiService;
    }

    @GetMapping("/summary")
    public String getSummary(@PathVariable String filePath)  {
        String document = pdfService.convertPDF(filePath);
        String summary = aiService.genSummary(document);
        return summary;
    }

    @GetMapping("/resourcefinder")
    public void findResources(@PathVariable String filePath, @PathVariable String prompt){
        String document = pdfService.convertPDF(filePath);
        PromptData data = new PromptData(prompt, document);
        //ResourceData keywords = aiService.getResourceData(data);
        //search wiki for keywords
    }

}
