package com.example.outbrain.openai;
import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.pdf.PDFService;
import com.example.outbrain.wikipedia.WikipediaService;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.web.bind.annotation.*;


@RestController
import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ApiController {
    private final WikipediaService wikipediaService;
    private final PDFService pdfService;
    private final AiService aiService;


    public ApiController(WikipediaService wikipediaService, PDFService pdfService, AzureOpenAiChatModel chatModel, AiService aiService){
        this.wikipediaService = wikipediaService;
        this.pdfService = pdfService;
        this.aiService = aiService;
    }

    @PostMapping("/summary")
    public DocumentSummary getSummary(@RequestBody String filePath)  {
        String document = pdfService.convertPDF(filePath);
        return aiService.genSummary(document);
    }

    @GetMapping("/resourcefinder")
    public void findResources(@PathVariable String filePath, @PathVariable String prompt, @PathVariable int limit){
        //convert pdf
        String document = pdfService.convertPDF(filePath);
        PromptData data = new PromptData(prompt, document);
        //ResourceData keywords = aiService.getResourceData(data);
        //search wiki for keywords

        //get keywords
        ResourceData keywords = gptApiClient.getResourceData(data);

        //search wiki for keywords
        wikipediaService.findByMultipleTitle(keywords.keywords(),limit);
    }

}
