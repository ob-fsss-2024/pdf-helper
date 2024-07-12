package com.example.outbrain.openai;
import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.PromptData;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.openai.client.dto.ResponseMode;
import com.example.outbrain.pdf.PDFService;
import com.example.outbrain.wikipedia.ShortWikiData;
import com.example.outbrain.wikipedia.WikipediaService;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

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

    @CrossOrigin(origins = "https://icy-smoke-05c513203.5.azurestaticapps.net/resourcefinder")
    @RequestMapping(path = "summary", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public DocumentSummary getSummary(@RequestParam("file") MultipartFile file, ResponseMode mode, int wordLimit)  {
        String document = pdfService.convertPDF(file);
        return aiService.genSummary(document, mode, wordLimit);
    }

    @CrossOrigin(origins = "https://icy-smoke-05c513203.5.azurestaticapps.net/resourcefinder")
    @RequestMapping(path = "resourcefinder", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public List<ShortWikiData> findResources(@RequestParam("file") MultipartFile file, String prompt, int limit){
        //convert pdf
        String document = pdfService.convertPDF(file);
        if(document.length() < 5){
            return null;
        }
        //get keywords
        ResourceData keywords = aiService.getKeywords(document);
        //search wiki for keywords
        return wikipediaService.findByMultipleTitle(keywords.keywords(),limit);
    }

}