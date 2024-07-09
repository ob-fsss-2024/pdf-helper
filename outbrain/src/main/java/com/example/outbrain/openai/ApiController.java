package com.example.outbrain.openai;
import com.example.outbrain.openai.client.GptApiClient;
import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.pdf.PDFService;
import com.example.outbrain.wikipedia.WikipediaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class ApiController {
    private final WikipediaService wikipediaService;
    private final GptApiClient gptApiClient;
    private final PDFService pdfService;
    public ApiController(WikipediaService wikipediaService, GptApiClient gptApiClient, PDFService pdfService){
        this.wikipediaService = wikipediaService;
        this.gptApiClient = gptApiClient;
        this.pdfService = pdfService;
    }

    @GetMapping("/summary")
    public DocumentSummary getSummary(@PathVariable String filePath, @PathVariable String prompt){
        String document = pdfService.convertPDF(filePath);
        DocumentSummary summary = gptApiClient.getDocumentSummary(document);
        return summary;
    }

    @GetMapping("/resourcefinder")
    public void findResources(@PathVariable String filePath, @PathVariable String prompt){
        String document = pdfService.convertPDF(filePath);
        PromptData data = new PromptData(prompt, document);
        ResourceData keywords = gptApiClient.getResourceData(data);
        //search wiki for keywords


    }

}
