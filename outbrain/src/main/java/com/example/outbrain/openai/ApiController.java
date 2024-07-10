package com.example.outbrain.openai;
import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.pdf.PDFService;
import com.example.outbrain.wikipedia.WikipediaService;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.web.bind.annotation.*;


import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@RestController
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

    @PostMapping("/resourcefinder")
    public List<WikipediaData> findResources(@RequestBody String filePath, String prompt, int limit){
        //convert pdf
        System.out.println(filePath);
        String document = pdfService.convertPDF(filePath);
        PromptData data = new PromptData(prompt, document);
        //ResourceData keywords = aiService.getResourceData(data);
        //search wiki for keywords

        //get keywords
        ResourceData keywords = aiService.getKeywords(document);
        System.out.println("this is nr of keywords retreived "+keywords.keywords().size());
        System.out.println("this is thw limit "+limit);

        //search wiki for keywords
        return wikipediaService.findByMultipleTitle(keywords.keywords(),limit);

    }

}
